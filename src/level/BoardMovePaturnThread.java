package level;

import org.joml.Vector2f;
import org.joml.Vector3f;

import gameobject.GameObject;
import gameobject.Muffet;
import gameobject.Player;
import undertale.Game;
import util.*;

public class BoardMovePaturnThread implements Runnable {
	GameObject gameBoard;
	Step step;
	Player player;
	Renderer renderer;
	Muffet muffet;
	public int checkPoint = 1;
	GameObject bug;

	BugAnimationThread bugAnimationThread;
	
	Thread thread1;

	public BoardMovePaturnThread(GameObject board, Step step, Player player, Renderer renderer, Muffet muffet,
			GameObject bug) {
		this.gameBoard = board;
		this.step = step;
		this.player = player;
		this.renderer = renderer;
		this.muffet = muffet;
		this.bug = bug;
		bugAnimationThread = new BugAnimationThread(bug);
	}

	@Override
	public void run() {
		try {
			step.setBoardMove(true);
			Thread.sleep(8500);
			animeStep++;
			Thread.sleep(2000);
			animeStep++;
			Thread.sleep(2000);
			animeStep++;
			Thread.sleep(400);
			Game.game.way.add(new GameObject(
					new Vector2f(gameBoard.getPosition().x + 50, gameBoard.getPosition().y + 60 + 0 * 80),
					new Vector2f(Game.game.BATTLE_WIDTH - 100, 2), new Vector3f(1f, 0f, 1f), null));
			Thread.sleep(250);
			Game.game.way.add(new GameObject(
					new Vector2f(gameBoard.getPosition().x + 50, gameBoard.getPosition().y + 60 + 1 * 80),
					new Vector2f(Game.game.BATTLE_WIDTH - 100, 2), new Vector3f(1f, 0f, 1f), null));
			Thread.sleep(250);
			Game.game.way.add(new GameObject(
					new Vector2f(gameBoard.getPosition().x + 50, gameBoard.getPosition().y + 60 + 2 * 80),
					new Vector2f(Game.game.BATTLE_WIDTH - 100, 2), new Vector3f(1f, 0f, 1f), null));
			player.setWaypos(player.getWaypos() + 3);
			thread1 = new Thread(bugAnimationThread);
			thread1.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void boardReset() {
		checkPoint++;
		if(Game.game.way.size() == 6) {
			for (int i = 3; i > 0; i--) {
				Game.game.way.remove(2 + i);
			}
		}
		float wayX = (Game.WIDTH - Game.game.BATTLE_WIDTH) / 2 + 50;
		float wayY = 355 + 60;

		for (int i = 0; i < 3; i++) {
			Game.game.way.get(i).setPosition(new Vector2f(wayX, wayY + i * 80));
		}
		player.resetPos();

	}
	
	public void reset() {
		moveCount = 0;
		animeStep = 0;
		checkPoint = 1;
		step.setBoardMove(false);
	}
	
	boolean first = true;
	
	public void stopThread() {
		if(thread1 != null) {
			thread1.interrupt();
		}
		reset();
	}
	

	public void bugBoardMoveMain() {
		System.out.println(animeStep);
		switch (checkPoint) {
		case 1:
			bugBoardMove1();
			break;
		case 2:
			bugBoardMove2();
			break;
		case 3:
			makeBug();
			break;
		case 4:
			bugBoardMove3();
			break;
		case 5:
			battleCloseMove();
			break;
		}

	}

	private void bugBoardMove1() {
		gameBoard.getPosition().x -= 10;
		player.getPosition().x -= 10;
		if (gameBoard.getPosition().x < 10) {
			checkPoint++;
		}
		for (int i = 0; i < Game.game.way.size(); i++) {
			Game.game.way.get(i)
					.setPosition(new Vector2f(gameBoard.getPosition().x + 50, gameBoard.getPosition().y + 60 + i * 80));
		}

	}

	int moveCount = 0;
	float boardSpeedSX = 650f / 14f / 60f;
	float boardSpeedSY = 90f / (8f / 12f) / 60f;

	private void bugBoardMove2() {
		gameBoard.getPosition().x += boardSpeedSX;
		gameBoard.getPosition().y += boardSpeedSY;
		player.getPosition().x += boardSpeedSX;
		player.getPosition().y += boardSpeedSY;
		if (gameBoard.getPosition().y > 390 || gameBoard.getPosition().y < 300) {
			moveCount++;
			boardSpeedSY *= -1;
		}
		if (moveCount == 12 && gameBoard.getPosition().y > 355) {
			checkPoint++;
		}
		for (int i = 0; i < Game.game.way.size(); i++) {
			Game.game.way.get(i)
					.setPosition(new Vector2f(gameBoard.getPosition().x + 50, gameBoard.getPosition().y + 60 + i * 80));
		}
		for (int i = 0; i < Game.game.spiders.size(); i++) {
			Game.game.spiders.get(i).getPosition().x += boardSpeedSX;
			Game.game.spiders.get(i).getPosition().y += boardSpeedSY;
		}
	}

	private int animeStep = 0;

	private void makeBug() {
		if (gameBoard.getSize().x < 600 && animeStep < 3) {
			gameBoard.getSize().x += 10;
		} else if (animeStep != 3) {
			renderer.drawSprite(ResourceManager.getTexture("bug" + Integer.toString(animeStep - 1)),
					new Vector2f(gameBoard.getPosition().x + 480, gameBoard.getPosition().y), new Vector2f(250, 250), 0,
					new Vector3f(1, 1, 1));
		} else {
			if (gameBoard.getSize().x > 480) {
				gameBoard.getSize().x -= 10;
			} else {
				checkPoint++;
				bug.setSpriteTexture(ResourceManager.getTexture("bug2"));
			}
		}

	}

	float boardSpeed = 3;
	int boardleft = 1;

	private void bugBoardMove3() {
		if (gameBoard.getSize().y < 520) {
			gameBoard.getSize().y += 5;
			gameBoard.getPosition().y -= 5;
			muffet.getPosition().y -= 5;
			player.getPosition().y -= 5;
			for (int i = 0; i < Game.game.way.size(); i++) {
				Game.game.way.get(i).setPosition(
						new Vector2f(gameBoard.getPosition().x + 50, gameBoard.getPosition().y + 60 + i * 80));
			}
		} else {
			for (int i = 0; i < Game.game.way.size(); i++) {
				Game.game.way.get(i).getPosition().y += 2;
				Game.game.way.get(i).getPosition().x += boardSpeed * boardleft;
			}
			player.getPosition().y += 2;
			player.getPosition().x += boardSpeed * boardleft;
			if (Game.game.way.get(5).getPosition().y > gameBoard.getPosition().y + gameBoard.getSize().y - 40) {
				for (int i = 0; i < Game.game.way.size(); i++) {
					Game.game.way.get(i).getPosition().y -= 80;
				}
				player.setWaypos(player.getWaypos() - 1);
				if (player.getWaypos() < 1) {
					PlayerHitThread pThread = new PlayerHitThread(player, 10);
					Thread thread = new Thread(pThread);
					thread.start();
					player.setWaypos(4);
					player.getPosition().y -= 320;
				} 
			}
			if(bug.getSpriteTexture() == null) {
				renderer.drawSprite(ResourceManager.getTexture("bug3"),
						new Vector2f(gameBoard.getPosition().x + 80,
								gameBoard.getPosition().y + gameBoard.getSize().y - (gameBoard.getSize().y - 400)),
						new Vector2f(gameBoard.getSize().y - 200, gameBoard.getSize().y - 400), 0, new Vector3f(1, 1, 1));
			}
			else {
				renderer.drawSprite(bug.getSpriteTexture(),
						new Vector2f(gameBoard.getPosition().x + 80,
								gameBoard.getPosition().y + gameBoard.getSize().y - (gameBoard.getSize().y - 400)),
						new Vector2f(gameBoard.getSize().y - 200, gameBoard.getSize().y - 400), 0, new Vector3f(1, 1, 1));
			}
			
			for (int i = 0; i < Game.game.spiders2.size(); i++) {
				Game.game.spiders2.get(i).getPosition().y += 2;
				Game.game.spiders2.get(i).getPosition().x += boardSpeed * boardleft;
			}
			gameBoard.getPosition().x += boardSpeed * boardleft;
			if (gameBoard.getPosition().x <= 80 || gameBoard.getPosition().x >= 680) {
				boardleft *= -1;
			}
		}
	}

	private void battleCloseMove() {
		if (muffet.getPosition().y < 0) {
			muffet.getPosition().y += 5;
		}
		if (gameBoard.getSize().y > 280) {
			gameBoard.getSize().y -= 5f;
		}
		if (gameBoard.getPosition().y < 355) {
			gameBoard.getPosition().y += 5;
		}
		if(gameBoard.getPosition().x < 360) {
			gameBoard.getPosition().x += 5;
		}
		if(gameBoard.getPosition().x > 365) {
			gameBoard.getPosition().x -= 5;;
		}
	}

}
