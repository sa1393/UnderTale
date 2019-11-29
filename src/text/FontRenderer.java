package text;

import org.joml.*;
import org.lwjgl.opengl.*;

import undertale.*;

public class FontRenderer {
	public static final String VERTEX_SHADER = "shaders/fontVertexShader.txt";
	public static final String FRAGMENT_SHADER = "shaders/fontFragmentShader.txt";
	
	private Shader shader;
	
	private int VAO;
	private int VBO;
	
	public FontRenderer(){
		shader = new Shader(VERTEX_SHADER, FRAGMENT_SHADER);
		
		shader.start();
		shader.setMatrix4f("projection", Game.game.getProjectionMatrix(Game.WIDTH, Game.HEIGHT));
		shader.stop();
		
		VAO = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(VAO);
		
		VBO = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBO);
		
		float[] vertices = new float[6 * 4];
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertices, GL15.GL_DYNAMIC_DRAW);
		
		GL20.glEnableVertexAttribArray(0);
		GL20.glVertexAttribPointer(0, 4, GL11.GL_FLOAT, false, 0, 0);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		GL30.glBindVertexArray(0);
	}
	
	public void renderString(FontTexture fontTexture, String text, float x, float y, Vector3f color, float scale){
		shader.start();
		
		shader.setVector3f("textColor", color);

		float drawX = x;
		float drawY = y;
		
		fontTexture.getTexture().bind();
		
		GL30.glBindVertexArray(VAO);
		GL20.glEnableVertexAttribArray(0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBO);
		
		for(int i=0; i< text.length(); i++){
			char ch = text.charAt(i);
			if(ch == '\n'){
				drawY += fontTexture.getHeight() * scale;
				drawX = x;
				continue;
			}
			if(ch == '\r'){
				continue;
			}
			
			CharInfo cInfo = fontTexture.charMap.get(ch);
			float w = cInfo.getWidth() * scale;
			float h = fontTexture.getHeight() * scale;
			
			float tsx = cInfo.getStartX() / (float)fontTexture.getWidth();
			float tfx = (cInfo.getStartX() + cInfo.getWidth()) / (float)fontTexture.getWidth();
			
			float[] vertices = {
					drawX,     drawY + h,   tsx, 1.0f ,
					drawX + w, drawY,       tfx, 0.0f ,
					drawX,     drawY,       tsx, 0.0f ,

					drawX,     drawY + h,   tsx, 1.0f ,
					drawX + w, drawY + h,   tfx, 1.0f ,
					drawX + w, drawY,       tfx, 0.0f 
		        };
			drawX += w;
			
			GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0, vertices);
			GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 6);
			
		}
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);
		
		shader.stop();
		
	}
}
