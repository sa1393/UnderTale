package undertale;

import java.sql.*;
import java.util.*;

import org.joml.*;
import org.lwjgl.glfw.*;

import gameobject.*;
import level.*;
import util.*;

public class UnderTale {
	private float speed;

	private int selectBtn;

	private Renderer renderer;

	public boolean[] keys = new boolean[1024];

	Player player;
	GameObject gameBoard;
	Muffet muffet;
	public Step level;
	int playerHP;
	int muffetHP;
	long start;
	long end;
	
	int turn = 1;
	
	float timeTaken;
	
	public void reset() {
		turn = 1;
		level.setLevel(turn);
		player.setFood(2);
		Game.game.spiders.clear();
		Game.game.boomerang.clear();
		Game.game.donut.clear();
		Game.game.spiders2.clear();
		Game.game.way.clear();

		player.resetPos();
		gameBoard.setPosition(new Vector2f(60f, 355f));
		gameBoard.setSize(new Vector2f(Game.game.TALK_WIDTH, Game.game.TALK_HEIGHT));

		player.setHp(20);
		muffet.setPosition(new Vector2f(430f, 0f));

		float wayX = (Game.WIDTH - Game.game.BATTLE_WIDTH) / 2 + 50;
		float wayY = gameBoard.getPosition().y + 60;
		for (int i = 0; i < 3; i++) {
			Game.game.way.add(new GameObject(new Vector2f(wayX, wayY + i * 80),
					new Vector2f(Game.game.BATTLE_WIDTH - 100, 2), new Vector3f(1f, 0f, 1f), null));
		}
		Game.game.damage = 0;
	}

	public void init() {
		speed = 4f;
		selectBtn = 1;
		Game.game.GAMESTATUS = GameStatus.GAMEOVER;
		renderer = new Renderer();
		muffet = new Muffet(new Vector2f(430f, 0f), new Vector2f(340f, 340f), Game.game.BLACK,
				ResourceManager.getTexture("muffet"), ResourceManager.getTexture("eye1"),
				ResourceManager.getTexture("2eye1"), ResourceManager.getTexture("3eye1"));
		gameBoard = new GameObject(new Vector2f(60f, 355f), new Vector2f(Game.game.TALK_WIDTH, Game.game.TALK_HEIGHT),
				new Vector3f(0f, 0f, 0f), null);
		player = new Player(new Vector2f(580f, 482f), new Vector2f(30f, 30f), Game.game.BLACK,
				ResourceManager.getTexture("Normal_heart"));

		Game.game.spiders.clear();
		Game.game.boomerang.clear();
		Game.game.donut.clear();
		Game.game.spiders2.clear();
		Game.game.way.clear();
		player.resetPos();

		float wayX = (Game.WIDTH - Game.game.BATTLE_WIDTH) / 2 + 50;
		float wayY = gameBoard.getPosition().y + 60;

		for (int i = 0; i < 3; i++) {
			Game.game.way.add(new GameObject(new Vector2f(wayX, wayY + i * 80),
					new Vector2f(Game.game.BATTLE_WIDTH - 100, 2), new Vector3f(1f, 0f, 1f), null));
		}
		level = new Step(gameBoard, renderer, player, muffet);
	}

	public UnderTale() {
		init();
	}
	
	public void processInput() {
		if (Game.game.GAMESTATUS == GameStatus.GAME) {
			float velocity = speed;
			if (keys[GLFW.GLFW_KEY_LEFT]) {
				if (player.getPosition().x > gameBoard.getPosition().x + 50) {
					player.getPosition().x -= velocity;
				}
			}

			if (keys[GLFW.GLFW_KEY_RIGHT]) {
				if (player.getPosition().x + player.getSize().x < gameBoard.getPosition().x + 50
						+ Game.game.way.get(1).getSize().x) {
					player.getPosition().x += velocity;
				}
			}

			if (keys[GLFW.GLFW_KEY_UP] && player.getWaypos() < Game.game.way.size()) {
				player.getPosition().y -= 80;
				player.setWaypos(player.getWaypos() + 1);
				keys[GLFW.GLFW_KEY_UP] = false;
			}
			if (keys[GLFW.GLFW_KEY_DOWN] && player.getWaypos() > 1) {
				player.getPosition().y += 80;
				player.setWaypos(player.getWaypos() - 1);
				keys[GLFW.GLFW_KEY_DOWN] = false;
			}

		} else if (Game.game.GAMESTATUS == GameStatus.SELECT) {
			if (keys[GLFW.GLFW_KEY_LEFT]) {
				if (selectBtn > 1) {
					selectBtn--;
				} else {
					selectBtn = 4;
				}
				keys[GLFW.GLFW_KEY_LEFT] = false;

			}

			if (keys[GLFW.GLFW_KEY_RIGHT]) {
				if (selectBtn < 4) {
					selectBtn++;
				} else {
					selectBtn = 1;
				}
				keys[GLFW.GLFW_KEY_RIGHT] = false;
			}

			if (keys[GLFW.GLFW_KEY_Z]) {
				keys[GLFW.GLFW_KEY_Z] = false;
				Game.game.GAMESTATUS = GameStatus.ITEM;
				level.setBoardChange(true);
			}
		} else if (Game.game.GAMESTATUS == GameStatus.ITEM) {
			if (keys[GLFW.GLFW_KEY_Z]) {
				keys[GLFW.GLFW_KEY_Z] = false;
				level.useItem();
				if(Game.game.GAMESTATUS != GameStatus.SELECT) {
					Game.game.GAMESTATUS = GameStatus.TALK;
				}
			}
			if (keys[GLFW.GLFW_KEY_X]) {
				keys[GLFW.GLFW_KEY_X] = false;
				Game.game.GAMESTATUS = GameStatus.SELECT;
			}

		} else if (Game.game.GAMESTATUS == GameStatus.TALK) {
			if (keys[GLFW.GLFW_KEY_Z]) {
				keys[GLFW.GLFW_KEY_Z] = false;
				Game.game.GAMESTATUS = GameStatus.GAME;
				level.level(turn);
				turn++;
				level.setLevel(turn);
				
				if(level.getLevel() - 1 == 17) {
					end = System.currentTimeMillis();
					timeTaken = (float) ((end - start) / 1000.0);
					Game.game.addTime(timeTaken, Game.game.damage);
					level.ending = true;
					Game.game.music.stop();
				}
			}
		} else if (Game.game.GAMESTATUS == GameStatus.GAMEOVER) {
			if (keys[GLFW.GLFW_KEY_Z]) {
				keys[GLFW.GLFW_KEY_Z] = false;
				Game.game.music = new Music("spider.mp3", true);
				Game.game.music.start();
				Game.game.GAMESTATUS = GameStatus.SELECT;
				start = System.currentTimeMillis();
			}
		}
	}

	public void update() {
		for (int i = 0; i < Game.game.spiders.size(); i++) {
			Game.game.spiders.get(i).getPosition().x += Game.game.spiders.get(i).getSpeed()
					* Game.game.spiders.get(i).getLeft();
			if (!Game.game.isHit) {
				if (Collision.checkCol(player, Game.game.spiders.get(i))) {
					PlayerHitThread pThread = new PlayerHitThread(player, 5);
					Thread thread = new Thread(pThread);
					thread.start();
				}
			}
		}

		for (int i = 0; i < Game.game.spiders2.size(); i++) {
			Game.game.spiders2.get(i).getPosition().x += Game.game.spiders2.get(i).getSpeed()
					* Game.game.spiders2.get(i).getLeft();
			if (Game.game.spiders2.get(i).getPosition().x < gameBoard.getPosition().x + 50
					|| Game.game.spiders2.get(i).getPosition().x > gameBoard.getPosition().x + gameBoard.getSize().x
							- 60) {
				Game.game.spiders2.get(i).setLeft(Game.game.spiders2.get(i).getLeft() * -1);
			}
			if (!Game.game.isHit) {
				if (Collision.checkCol(player, Game.game.spiders2.get(i))) {
					PlayerHitThread pThread = new PlayerHitThread(player, 5);
					Thread thread = new Thread(pThread);
					thread.start();
				}
			}
		}

		for (int i = 0; i < Game.game.donut.size(); i++) {
			Game.game.donut.get(i).getPosition().x += Game.game.donut.get(i).getSpeed().x
					* Game.game.donut.get(i).getLeft();
			switch (Game.game.donut.get(i).getWay()) {
			case 1:
				Game.game.donut.get(i).getPosition().y += Game.game.donut.get(i).getSpeed().y;
				break;
			case 3:
				Game.game.donut.get(i).getPosition().y -= Game.game.donut.get(i).getSpeed().y;
				break;
			}

			if (Game.game.donut.get(i).getPosition().y < gameBoard.getPosition().y
					|| Game.game.donut.get(i).getPosition().y
							+ Game.game.donut.get(i).getSize().y > gameBoard.getPosition().y
									+ Game.game.BATTLE_HEIGHT) {
				Game.game.donut.get(i).setSpeed(
						new Vector2f(Game.game.donut.get(i).getSpeed().x, Game.game.donut.get(i).getSpeed().y * -1));
			}
			if (!Game.game.isHit) {
				if (Collision.checkCol(player, Game.game.donut.get(i))) {
					PlayerHitThread pThread = new PlayerHitThread(player, 5);
					Thread thread = new Thread(pThread);
					thread.start();
				}
			}
		}

		for (int i = 0; i < Game.game.boomerang.size(); i++) {
			float speed = Game.game.boomerang.get(i).getSpeed();
			double speedMinus = Game.game.boomerang.get(i).getSpeedMinus();
			float rotate = Game.game.boomerang.get(i).getRotate();

			Game.game.boomerang.get(i).setRotate((rotate += 5) % 360);
			Game.game.boomerang.get(i).getPosition().x += speed * Game.game.boomerang.get(i).getLeft();
			Game.game.boomerang.get(i).setSpeed(speed -= speedMinus);

			if (!Game.game.isHit) {
				if (Collision.checkCol(player, Game.game.boomerang.get(i))) {
					PlayerHitThread pThread = new PlayerHitThread(player, 5);
					Thread thread = new Thread(pThread);
					thread.start();
				}
			}
		}

	}
	
	

	public void render() {
		if(level.ending) {
			level.ending(timeTaken, Game.game.damage);
		}
		else {
			renderer.start();
			update();

			renderer.draw(gameBoard.getPosition(), gameBoard.getSize(), gameBoard.getPlusRotate(), gameBoard.getColor());

			if (Game.game.GAMESTATUS != GameStatus.GAMEOVER && level.ending == false) {
				level.ui(selectBtn);
			}

			level.render();

			for (int i = 0; i < Game.game.spiders.size(); i++) {
				try {
					renderer.drawSprite(Game.game.spiders.get(i).getSpriteTexture(), Game.game.spiders.get(i).getPosition(),
							Game.game.spiders.get(i).getSize(), 0, new Vector3f(1, 1, 1));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			for (int i = 0; i < Game.game.spiders2.size(); i++) {
				try {
					renderer.drawSprite(Game.game.spiders2.get(i).getSpriteTexture(),
							Game.game.spiders2.get(i).getPosition(), Game.game.spiders2.get(i).getSize(), 0,
							new Vector3f(1, 1, 1));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			for (int i = 0; i < Game.game.donut.size(); i++) {
				try {
					renderer.drawSprite(Game.game.donut.get(i).getSpriteTexture(), Game.game.donut.get(i).getPosition(),
							Game.game.donut.get(i).getSize(), 0, new Vector3f(1, 1, 1));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			for (int i = 0; i < Game.game.boomerang.size(); i++) {
				try {
					renderer.drawSprite(Game.game.boomerang.get(i).getSpriteTexture(),
							Game.game.boomerang.get(i).getPosition(), Game.game.boomerang.get(i).getSize(),
							Game.game.boomerang.get(i).getRotate(), new Vector3f(1, 1, 1));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (Game.game.GAMESTATUS != GameStatus.GAMEOVER) {
				level.textRender();
			}

			renderer.stop();
		}
		
	}
}
