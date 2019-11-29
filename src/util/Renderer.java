package util;

import java.lang.Math;

import org.joml.*;
import org.lwjgl.opengl.*;

import undertale.*;
import util.*;

public class Renderer {
	private Shader shader;
	
	private static final String VERTEX_FILE = "shaders/VertexShader.txt";
	private static final String FRAGMENT_FILE = "shaders/FragmentShader.txt";

	public Renderer() {
		shader = new Shader(VERTEX_FILE, FRAGMENT_FILE);

		shader.start();
		shader.setMatrix4f("projection", Game.getProjectionMatrix());
		shader.stop();
	}

	Matrix4f model = new Matrix4f();

	public void drawSprite(Texture texture, Vector2f pos, Vector2f size, float rotate, Vector3f color) {
		model.identity();
		float rotatePosX = 0;
		float rotatePosY = 0;
		if(rotate < 180) {
			rotatePosX = (0 + rotate) * (size.x / 180);
			rotatePosY = (0 + rotate) * (size.y / 180);
		}
		else {
			rotatePosX = (360 - rotate) * (size.x / 180);
			rotatePosY = (360 - rotate) * (size.y / 180);
		}
		
		model.translate(pos.x + rotatePosX, pos.y+rotatePosY , 0);
		model.scale(size.x, size.y, 1);
		
		model.rotateZ((float)Math.toRadians(rotate));
		
		
		shader.setMatrix4f("model", model);
		shader.setVector3f("spriteColor", color);
		shader.setInteger("imageOn", 1);

		texture.bind();
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 6);
		texture.unbind();
	}

	public void draw(Vector2f pos, Vector2f size, float rotate, Vector3f color) {
		model.identity();
		model.translate(pos.x, pos.y, 0);
		model.scale(size.x, size.y, 1);
		
		shader.setMatrix4f("model", model);
		shader.setVector3f("spriteColor", color);
		shader.setInteger("imageOn", 0);
		
		
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 6);
	}

	public void start() {
		shader.start();
		GL30.glBindVertexArray(ResourceManager.quadVAO);
		GL20.glEnableVertexAttribArray(0);
	}

	public void stop() {
		shader.stop();
		GL30.glBindVertexArray(0);
		GL20.glDisableVertexAttribArray(0);
	}
}
