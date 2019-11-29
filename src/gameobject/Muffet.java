package gameobject;

import org.joml.Vector2f;
import org.joml.Vector3f;

import util.Texture;

public class Muffet extends GameObject{
	private Texture eye1;
	private Texture eye2;
	private Texture eye3;
	
	public Muffet(Vector2f position, Vector2f size, Vector3f color, Texture spriteTexture, Texture eye1, Texture eye2, Texture eye3) {
		super(position, size, color, spriteTexture);
		this.eye1 = eye1;
		this.eye2 = eye2;
		this.eye3 = eye3;
	}

	public Texture getEye1() {
		return eye1;
	}

	public void setEye1(Texture eye1) {
		this.eye1 = eye1;
	}

	public Texture getEye2() {
		return eye2;
	}

	public void setEye2(Texture eye2) {
		this.eye2 = eye2;
	}

	public Texture getEye3() {
		return eye3;
	}

	public void setEye3(Texture eye3) {
		this.eye3 = eye3;
	}
	
}
