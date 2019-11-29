package util;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glTexParameteri;

import java.nio.*;

import org.lwjgl.opengl.*;
import org.lwjgl.stb.*;
import org.lwjgl.system.*;

import undertale.Game;

public class Texture {
	private int id;

	private int width;
	private int height;

	public Texture(String path) {
		ByteBuffer image;
		IntBuffer w = MemoryUtil.memAllocInt(1);
		IntBuffer h = MemoryUtil.memAllocInt(1);
		IntBuffer comp = MemoryUtil.memAllocInt(1);


		image = STBImage.stbi_load("res/"+path, w, h, comp, 4);

		if (image == null) {
			throw new RuntimeException(
					"Failed to load a texture file!" + System.lineSeparator() + STBImage.stbi_failure_reason());
		}

		this.id = GL11.glGenTextures();

		this.width = w.get(0);
		this.height = h.get(0);

		this.bind();

		this.setParameter(GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
		this.setParameter(GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
		this.setParameter(GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		this.setParameter(GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, image);
		this.unbind();
		STBImage.stbi_image_free(image);
	}

	public Texture(int width, int height, ByteBuffer data) {
		this.width = width;
		this.height = height;
		
		// create texture
		this.id = GL11.glGenTextures();
		
		// bind texture
		this.bind();
		
		this.setParameter(GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
		this.setParameter(GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
		this.setParameter(GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		this.setParameter(GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

		// put image data.
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0,GL11.GL_RGBA8, width,
				height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, data);
				
		// unbind + free data.
		this.unbind();
	}

	public int getId() {
		return id;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setParameter(int name, int value) {
		glTexParameteri(GL_TEXTURE_2D, name, value);
	}

	public void bind() {
		glBindTexture(GL_TEXTURE_2D, this.id);
	}

	public void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}
}
