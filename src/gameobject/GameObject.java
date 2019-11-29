package gameobject;

import org.joml.*;

import util.Texture;

public class GameObject {
	private Vector2f position;
	private Vector2f size;

	private Vector3f color;
	private Vector2f velocity;

	private Texture spriteTexture;

	public void setSpriteTexture(Texture spriteTexture) {
		this.spriteTexture = spriteTexture;
	}

	private boolean isSolid = false;
	private boolean isDestroyed = false;
	
	private float plusRotate;

	public float getPlusRotate() {
		return plusRotate;
	}

	public void setPlusRotate(float plusRotate) {
		this.plusRotate = plusRotate;
	}

	public GameObject(Vector2f position, Vector2f size, Vector3f color, Texture spriteTexture) {
		this.position = position;
		this.size = size;
		this.color = color;
		this.spriteTexture = spriteTexture;
	}

	public boolean isDestroyed() {
		return isDestroyed;
	}

	public void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}

	public Vector2f getPosition() {
		return position;
	}
	
	public void setPosition(Vector2f position) {
		this.position = position; 
	}

	public Vector2f getSize() {
		return size;
	}

	public Vector2f getVelocity() {
		return velocity;
	}

	public Vector3f getColor() {
		return color;
	}

	public void setSize(Vector2f size) {
		this.size = size;
	}

	public void setColor(Vector3f color) {
		this.color = color;
	}

	public void setVelocity(Vector2f velocity) {
		this.velocity = velocity;
	}

	public Texture getSpriteTexture() {
		return spriteTexture;
	}

}
