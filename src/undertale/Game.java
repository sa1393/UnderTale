package undertale;

import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

import java.sql.*;
import java.util.*;

import org.joml.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import gameobject.*;
import util.*;
public class Game {
	public static Game game;

	public Music music;
	private long window;
	
	public int damage;

	public UnderTale undertale;

	public static int WIDTH = 1200;
	public static int HEIGHT = 800;

	public float BATTLE_WIDTH = 480;
	public float BATTLE_HEIGHT = 280;
	public float TALK_WIDTH = 1080;
	public float TALK_HEIGHT = 280;

	public boolean isHit = false;

	public GameStatus GAMESTATUS = GameStatus.GAMEOVER;
	
	public ArrayList<GameObject> way = new ArrayList<GameObject>();
	public ArrayList<Spider> spiders = new ArrayList<Spider>();
	public ArrayList<Spider> spiders2 = new ArrayList<Spider>();
	public ArrayList<Donut> donut = new ArrayList<Donut>();
	public ArrayList<Boomerang> boomerang = new ArrayList<Boomerang>();
	public ArrayList<ClearVO> clearMans = new ArrayList<ClearVO>();
	
	public Vector3f BLACK = new Vector3f(0, 0, 0);

	public static Matrix4f getProjectionMatrix() {
		Matrix4f mat = new Matrix4f();
		mat.ortho2D(0, WIDTH, HEIGHT, 0);
		return mat;
	}

	public Matrix4f getProjectionMatrix(int width, int height) {
		Matrix4f mat = new Matrix4f();
		mat.ortho2D(0, width, height, 0);
		return mat;
	}

	public void run() {
		reloadTime();
		
		initGLFW();
		initOpenGL();
		ResourceManager.init();
		undertale = new UnderTale();

		loop();
		terminate();
	}

	private void initGLFW() {
		GLFWErrorCallback.createPrint(System.err).set();

		if (!GLFW.glfwInit()) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}

		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);

		window = GLFW.glfwCreateWindow(WIDTH, HEIGHT, "undertale", MemoryUtil.NULL, MemoryUtil.NULL);
		if (window == MemoryUtil.NULL) {
			throw new RuntimeException("Failed to create the GLFW window");
		}

		GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		GLFW.glfwSetWindowPos(window, (vidMode.width() - WIDTH) / 2, (vidMode.height() - HEIGHT) / 2);

		GLFWKeyCallback keyCallback = new GLFWKeyCallback() {
			@Override
			public void invoke(long window, int key, int scancode, int action, int mods) {
				if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_PRESS) {
					GLFW.glfwSetWindowShouldClose(window, true);
					if(music != null) {
						music.close();
					}
					undertale.level.stopLevelThread();
				}

				if (key >= 0 && key < 1024) {
					if (action == GLFW.GLFW_PRESS)
						undertale.keys[key] = true;
					else if (action == GLFW.GLFW_RELEASE)
						undertale.keys[key] = false;
				}
			}
		};
		GLFW.glfwSetKeyCallback(window, keyCallback);
		GLFW.glfwMakeContextCurrent(window);
		GLFW.glfwSwapInterval(1);
		GLFW.glfwShowWindow(window);
	}

	private void initOpenGL() {
		GL.createCapabilities();
		GL11.glViewport(0, 0, WIDTH, HEIGHT);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}

	private void loop() {
		boolean running = true;
		long lastTime = System.nanoTime();
		double delta = 0.0;
		double ns = 1000000000.0 / 60.0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1.0) {
				updates++;
				delta--;
				GLFW.glfwPollEvents();

				undertale.processInput();

				GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
				undertale.render();

				GLFW.glfwSwapBuffers(window);
			}
			frames++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				updates = 0;
				frames = 0;
			}
			if (glfwWindowShouldClose(window) == true) {
				running = false;
				Game.game.undertale.level.stopLevelThread();
				if(music != null) {
					music.close();	
				}
				
				undertale.level.stopLevelThread();
			}
		}
	}

	private void terminate() {
		ResourceManager.cleanUp();
		Callbacks.glfwFreeCallbacks(window);
		GLFW.glfwDestroyWindow(window);
		GLFW.glfwTerminate();
		GLFW.glfwSetErrorCallback(null).free();
	}

	public static void main(String[] args) {
		game = new Game();
		game.run();
	}
	
	public void addTime(float time, int hp) {
		Connection con = JDBCUtil.getConnection();
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO UnderTale (timer, HP) VALUES (?, ?)";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setFloat(1, time);
			pstmt.setInt(2, hp);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(pstmt);
			JDBCUtil.close(con);
		}
	}

	public void reloadTime() {
		clearMans.clear();
		Connection con = JDBCUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM UnderTale ORDER BY HP ASC limit 0, 4";
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ClearVO temp = new ClearVO();
				float timer;
				int Hp;
				timer = rs.getFloat("timer");
				Hp = rs.getInt("HP");
				temp.setTimer(timer);
				temp.setHP(Hp);
				clearMans.add(temp);
			}

		} catch (Exception e) {
		} finally {
			JDBCUtil.close(rs);
			JDBCUtil.close(pstmt);
			JDBCUtil.close(con);
		}
	}

}
