package gameobject;

import org.joml.*;

import undertale.Game;
import util.*;

public class Player extends GameObject {
	private int Hp = 20;
	private int waypos;
	private int food = 2;
	
	public int getFood() {
		return food;
	}

	public void setFood(int food) {
		this.food = food;
	}

	public int getHp() {
		return Hp;
	}

	public void setHp(int hp) {
		Hp = hp;
	}

	public int getWaypos() {
		return waypos;
	}

	public void setWaypos(int waypos) {
		this.waypos = waypos;
	}

	public Player(Vector2f position, Vector2f size, Vector3f color, Texture spriteTexture) {
		super(position, size, color, spriteTexture);
	}
	
	
	public void hit(int damage) {
		
	}
	
	public void resetPos() {
		setPosition(new Vector2f(580f, 482f));
		setWaypos(2);
	}
	

}
