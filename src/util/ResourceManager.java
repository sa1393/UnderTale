package util;

import java.util.*;

import org.lwjgl.opengl.*;

import undertale.*;


public class ResourceManager {
	
	public static Map<String,Texture> textureDictionary = new HashMap<String,Texture>();
	
	public static int quadVAO;
	private static List<Integer> vaoIDs = new ArrayList<Integer>();
	private static List<Integer> vboIDs = new ArrayList<Integer>();
	
	static float[] quadVertices = { 
			// Pos      // Tex
	        0.0f, 1.0f, 0.0f, 1.0f,
	        1.0f, 0.0f, 1.0f, 0.0f,
	        0.0f, 0.0f, 0.0f, 0.0f, 
	        0.0f, 1.0f, 0.0f, 1.0f,
	        1.0f, 1.0f, 1.0f, 1.0f,
	        1.0f, 0.0f, 1.0f, 0.0f
		};
	
	public static int loadRenderData(float[] data){
		int VAO = GL30.glGenVertexArrays();
		vaoIDs.add(VAO);
		GL30.glBindVertexArray(VAO);
		
		int VBO = GL15.glGenBuffers();
		vboIDs.add(VAO);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBO);
		
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(0, 4, GL11.GL_FLOAT, false, 0, 0);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		GL30.glBindVertexArray(0);
		
		return VAO;
	}
	
	public static void loadTexture(String name, String path){
		textureDictionary.put(name, new Texture(path));
	}
	
	public static Texture getTexture(String name){
		return textureDictionary.get(name);
	}
	
	public static void init(){
		loadTexture("Attack_Button", "Attack_Button.png");
		loadTexture("Attack_Button_On", "Attack_Button_On.png");
		loadTexture("Fight_Button", "Fight_Button.png");
		loadTexture("Fight_Button_On", "Fight_Button_On.png");
		loadTexture("Item_Button", "Item_Button.png");
		loadTexture("Item_Button_On", "Item_Button_On.png");
		loadTexture("Mercy_Button", "Mercy_Button.png");
		loadTexture("Mercy_Button_On", "Mercy_Button_On.png");
		
		loadTexture("muffet", "muffet.png");
		loadTexture("Normal_heart", "Normal_heart.png");
		loadTexture("Damage_heart", "Damage_heart.png");
		loadTexture("spider", "spider.png");
		loadTexture("boomerang", "boomerang.png");
		loadTexture("donut", "donut.png");
		loadTexture("logo", "logo.jpg");
		loadTexture("talkBox", "Talk_Box.png");
		
		loadTexture("bug0", "bug0.png");
		loadTexture("bug1", "bug1.png");
		loadTexture("bug2", "bug2.png");
		loadTexture("bug3", "bug3.png");
		loadTexture("bug4", "bug4.png");
		loadTexture("bug5", "bug5.png");

		quadVAO = loadRenderData(quadVertices);
	}
	
	public static void cleanUp(){
		for(int vaoID :vaoIDs){
			GL30.glDeleteVertexArrays(vaoID);
		}
		for(int vboID :vboIDs){
			GL15.glDeleteBuffers(vboID);
		}
	}
}
