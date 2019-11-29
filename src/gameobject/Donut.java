package gameobject;

import org.joml.Vector2f;
import org.joml.Vector3f;

import util.Texture;

public class Donut extends GameObject{
	private Vector2f speed = new Vector2f();
	private int left = 0;
	private int way = 1;
	private Vector2f position;
	private Vector2f size;
	
	
	public Donut(Vector2f position, Vector2f size, Vector3f color, Texture spriteTexture, int left, Vector2f speed, int way) {
		super(position, size, color, spriteTexture);
		this.way = way;
		this.speed = speed;
		this.left = left;
	}


	public Vector2f getSpeed() {
		return speed;
	}


	public void setSpeed(Vector2f speed) {
		this.speed = speed;
	}


	public int getLeft() {
		return left;
	}


	public void setLeft(int left) {
		this.left = left;
	}


	public int getWay() {
		return way;
	}


	public void setWay(int way) {
		this.way = way;
	}

}
