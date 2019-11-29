package gameobject;

import org.joml.Vector2f;
import org.joml.Vector3f;

import util.Texture;

public class Boomerang extends GameObject{
	private float speed = 0f;
	private int left = 0;
	private int way = 1;
	private double speedMinus;
	private Vector2f position;
	private Vector2f size;
	private float rotate;
	
	public Boomerang(Vector2f position, Vector2f size, Vector3f color, Texture spriteTexture, int left, float speed, int way, double speedMinus, float rotate) {
		super(position, size, color, spriteTexture);
		this.way = way;
		this.speed = speed;
		this.left = left;
		this.speedMinus = speedMinus;
		this.rotate = rotate;
	}


	public double getSpeedMinus() {
		return speedMinus;
	}


	public void setSpeedMinus(double d) {
		this.speedMinus = d;
	}


	public float getRotate() {
		return rotate;
	}


	public void setRotate(float rotate) {
		this.rotate = rotate;
	}


	public float getSpeed() {
		return speed;
	}


	public void setSpeed(float speed) {
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
