package level;

import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Random;

import org.joml.Vector2f;
import org.joml.Vector3f;

import gameobject.Boomerang;
import gameobject.Donut;
import gameobject.GameObject;
import gameobject.Muffet;
import gameobject.Player;
import gameobject.Spider;
import text.FontRenderer;
import text.FontTexture;
import undertale.Game;
import util.GameStatus;
import util.Renderer;
import util.ResourceManager;

public class Step {
	GameObject gameBoard;
	Muffet muffet;
	GameObject bug;

	Renderer renderer;
	Player player;
	FontRenderer fontRenderer;
	FontTexture fontTexture;
	FontTexture fontTexturekr;
	TalkData levelData;
	BoardMovePaturnThread boardMovePaturnThread;

	Vector3f playerColor = new Vector3f(1, 1, 1);
	int level = 1;
	Font font = null;

	private boolean boardChange = false;
	public boolean ending = false;

	LevelThread levelThread;
	Thread thread;

	public void stopLevelThread() {
		if (thread != null) {
			thread.interrupt();
			System.out.println(1);
		}
		if (levelThread.getThread() != null) {
			levelThread.getThread().interrupt();
			System.out.println(2);
		}
		boardMovePaturnThread.stopThread();
	}

	private void drawMuffet() {
		renderer.drawSprite(muffet.getSpriteTexture(), muffet.getPosition(), muffet.getSize(), 0,
				new Vector3f(1, 1, 1));
//		renderer.drawSprite(muffet.getEye1(), new Vector2f(muffet.getPosition().x+135, muffet.getPosition().y+76), new Vector2f(25, 25), 0,
//				new Vector3f(1, 1, 1));
//		renderer.drawSprite(muffet.getEye1(), new Vector2f(muffet.getPosition().x+195, muffet.getPosition().y+60), new Vector2f(25, 25), 90,
//				new Vector3f(1, 1, 1));
//		renderer.drawSprite(muffet.getEye2(), new Vector2f(muffet.getPosition().x+145, muffet.getPosition().y+58), new Vector2f(15, 15), 0,
//				new Vector3f(1, 1, 1));
//		renderer.drawSprite(muffet.getEye2(), new Vector2f(muffet.getPosition().x+185, muffet.getPosition().y+58), new Vector2f(15, 15), 90,
//				new Vector3f(1, 1, 1));
//		renderer.drawSprite(muffet.getEye3(), new Vector2f(muffet.getPosition().x+172, muffet.getPosition().y+35), new Vector2f(20, 20), 90,
//				new Vector3f(1, 1, 1));

	}

	public Step(GameObject gameBoard, Renderer renderer, Player player, Muffet muffet) {
		this.gameBoard = gameBoard;
		this.renderer = renderer;
		this.player = player;
		this.muffet = muffet;

		font = null;
		try {
			InputStream is = new FileInputStream(new File("font/DTM-Sans.otf"));
			font = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.PLAIN, 24);
		} catch (Exception e) {
			e.printStackTrace();
		}
		fontTexture = new FontTexture(font);

		font = null;
		try {
			InputStream is = new FileInputStream(new File("font/NanumGothicBold.ttf"));
			font = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.PLAIN, 24);
		} catch (Exception e) {
			e.printStackTrace();
		}
		fontRenderer = new FontRenderer();
		levelData = new TalkData();
		fontTexturekr = new FontTexture(font);

		bug = new GameObject(
				new Vector2f(gameBoard.getPosition().x + 200,
						gameBoard.getPosition().y + gameBoard.getSize().y - (gameBoard.getSize().y - 400)),
				new Vector2f(gameBoard.getSize().y - 200, gameBoard.getSize().y - 400), new Vector3f(1, 1, 1),
				ResourceManager.getTexture("bug1"));
		boardMovePaturnThread = new BoardMovePaturnThread(gameBoard, this, player, renderer, muffet, bug);
		levelThread = new LevelThread(this, boardMovePaturnThread);
		thread = new Thread(levelThread, "level");
	}

	public int getLevel() {
		return level;
	}

	public void level(int level) {
		thread = new Thread(levelThread, "level");
		thread.start();
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void makeSpider(int left, int way, float speed) {
		Game.game.spiders.add(new Spider(
				new Vector2f(gameBoard.getPosition().x + gameBoard.getSize().x / 2 - (left * Game.game.BATTLE_WIDTH),
						(gameBoard.getPosition().y + 33) + (way - 1) * 80),
				new Vector2f(50f, 50f), new Vector3f(1, 1, 1), ResourceManager.getTexture("spider"), left, speed, way));
	}

	public void makeSpider2(int left, float speed) {
		float startPosX;
		Random random = new Random();
		startPosX = gameBoard.getPosition().x + 60 + (360 / (random.nextInt(10) + 1));
		Game.game.spiders2.add(new Spider(new Vector2f(startPosX, Game.game.way.get(0).getPosition().y - 110),
				new Vector2f(50f, 50f), new Vector3f(1, 1, 1), ResourceManager.getTexture("spider"), left, speed, 0));

	}

	public void makeDonut(int left, int way, float speed) {
		float positionX = gameBoard.getPosition().x + gameBoard.getSize().x / 2 - (left * Game.game.BATTLE_WIDTH);

		float speedY = (float) (Math.sqrt(Math.pow(Game.game.BATTLE_WIDTH - 480, 2) + Math.pow(220, 2))
				/ Game.game.BATTLE_WIDTH);
		Game.game.donut.add(new Donut(new Vector2f(positionX, (gameBoard.getPosition().y + 33) + (way - 1) * 80),
				new Vector2f(45f, 45f), new Vector3f(1, 1, 1), ResourceManager.getTexture("donut"), left,
				new Vector2f(speed, speed * speedY), way));
	}

	public void makeBoomerang(int left, int way, float speed) {
		double speedMinus = speed / (186 - 6.25 * speed);
		Game.game.boomerang.add(new Boomerang(
				new Vector2f(gameBoard.getPosition().x + gameBoard.getSize().x / 2 - (left * Game.game.BATTLE_WIDTH),
						(gameBoard.getPosition().y + 33) + (way - 1) * 80),
				new Vector2f(50f, 50f), new Vector3f(1, 1, 1), ResourceManager.getTexture("boomerang"), left, speed,
				way, speedMinus, 180));
	}

	public void setBoardChange(boolean change) {
		boardChange = change;
	}

	private void inBattle() {
		for (int i = 0; i < Game.game.way.size(); i++) {
			renderer.draw(Game.game.way.get(i).getPosition(), Game.game.way.get(i).getSize(), 0,
					Game.game.way.get(i).getColor());
		}
		renderer.draw(gameBoard.getPosition(), new Vector2f(gameBoard.getSize().x, 5), gameBoard.getPlusRotate(),
				new Vector3f(1f, 1f, 1f));
		renderer.draw(new Vector2f(gameBoard.getPosition().x, gameBoard.getPosition().y + gameBoard.getSize().y),
				new Vector2f(gameBoard.getSize().x, 5), 0, new Vector3f(1f, 1f, 1f));
		renderer.draw(gameBoard.getPosition(), new Vector2f(5, gameBoard.getSize().y), 0, new Vector3f(1f, 1f, 1f));
		renderer.draw(new Vector2f(gameBoard.getPosition().x + gameBoard.getSize().x, gameBoard.getPosition().y),
				new Vector2f(5, gameBoard.getSize().y), 0, new Vector3f(1f, 1f, 1f));
		if (boardChange) {
			if (gameBoard.getSize().x > 480) {
				gameBoard.getSize().x -= 20f;
				gameBoard.getPosition().x += 10f;
			} else {
				boardChange = false;
			}
		}

		renderer.drawSprite(player.getSpriteTexture(), player.getPosition(), player.getSize(), 0,
				new Vector3f(1, 1, 1));
	}

	int itemNum = 0;

	private void inItem() {
		renderer.draw(gameBoard.getPosition(), new Vector2f(gameBoard.getSize().x, 5), 0, new Vector3f(1f, 1f, 1f));
		renderer.draw(new Vector2f(gameBoard.getPosition().x, gameBoard.getPosition().y + gameBoard.getSize().y),
				new Vector2f(gameBoard.getSize().x, 5), 0, new Vector3f(1f, 1f, 1f));
		renderer.draw(gameBoard.getPosition(), new Vector2f(5, gameBoard.getSize().y), 0, new Vector3f(1f, 1f, 1f));
		renderer.draw(new Vector2f(gameBoard.getPosition().x + gameBoard.getSize().x, gameBoard.getPosition().y),
				new Vector2f(5, gameBoard.getSize().y), 0, new Vector3f(1f, 1f, 1f));
		if (gameBoard.getSize().x < 1080) {
			gameBoard.getSize().x += 20f;
			gameBoard.getPosition().x -= 10f;
		}
		else {
			switch (selectBtn) {
			case 1:
				fontTexturekr = new FontTexture(font, "머펫");
				fontRenderer.renderString(fontTexturekr, ">머펫", 80, 380, new Vector3f(1, 1, 1), 1.7f);
				break;
			case 2:
				fontTexturekr = new FontTexture(font, "멍때리기");
				fontRenderer.renderString(fontTexturekr, ">멍때리기", 80, 380, new Vector3f(1, 1, 1), 1.7f);
				break;
			case 3:
				itemNum = 1;
				fontTexturekr = new FontTexture(font, "비상식량", "회복", "수량");
				fontRenderer.renderString(fontTexturekr, ">비상식량 (HP 6 회복) 수량 : " + player.getFood(), 80, 380,
						new Vector3f(1, 1, 1), 1.7f);
				break;
			case 4:
				fontTexturekr = new FontTexture(font, "머펫");
				fontRenderer.renderString(fontTexturekr, ">머펫", 80, 380, new Vector3f(1, 1, 1), 1.7f);
				break;
			}
		}
		
	}

	public void useItem() {
		switch (selectBtn) {
		case 1:
			break;
		case 2:
			break;
		case 3:
			if (itemNum == 1) {
				if (player.getFood() <= 0) {
					Game.game.GAMESTATUS = GameStatus.SELECT;
					return;
				}
				int playerHeal = player.getHp();
				playerHeal += 6;
				if (playerHeal > 20) {
					playerHeal = 20;
				}
				player.setHp(playerHeal);
				player.setFood(player.getFood() - 1);
			}
			break;
		case 4:
			break;
		}
	}

	private void inTalk() {
		renderer.draw(gameBoard.getPosition(), new Vector2f(gameBoard.getSize().x, 5), 0, new Vector3f(1f, 1f, 1f));
		renderer.draw(new Vector2f(gameBoard.getPosition().x, gameBoard.getPosition().y + gameBoard.getSize().y),
				new Vector2f(gameBoard.getSize().x, 5), 0, new Vector3f(1f, 1f, 1f));
		renderer.draw(gameBoard.getPosition(), new Vector2f(5, gameBoard.getSize().y), 0, new Vector3f(1f, 1f, 1f));
		renderer.draw(new Vector2f(gameBoard.getPosition().x + gameBoard.getSize().x, gameBoard.getPosition().y),
				new Vector2f(5, gameBoard.getSize().y), 0, new Vector3f(1f, 1f, 1f));

		for (GameObject ways : Game.game.way) {
			renderer.draw(ways.getPosition(), ways.getSize(), 0, ways.getColor());
		}

		if (gameBoard.getSize().x > 480) {
			gameBoard.getSize().x -= 20f;
			gameBoard.getPosition().x += 10f;
		}

		renderer.drawSprite(player.getSpriteTexture(), player.getPosition(), player.getSize(), 0,
				new Vector3f(1, 1, 1));
		renderer.drawSprite(ResourceManager.getTexture("talkBox"), new Vector2f(750, 10), new Vector2f(360, 165), 0,
				new Vector3f(1, 1, 1));
		fontTexturekr = new FontTexture(font, levelData.talk(level));
		fontRenderer.renderString(fontTexturekr, levelData.talk(level), 810, 25, new Vector3f(0, 0, 0), 1.0f);
	}

	private void inSelect() {
		renderer.draw(gameBoard.getPosition(), new Vector2f(gameBoard.getSize().x, 5), 0, new Vector3f(1f, 1f, 1f));
		renderer.draw(new Vector2f(gameBoard.getPosition().x, gameBoard.getPosition().y + gameBoard.getSize().y),
				new Vector2f(gameBoard.getSize().x, 5), 0, new Vector3f(1f, 1f, 1f));
		renderer.draw(gameBoard.getPosition(), new Vector2f(5, gameBoard.getSize().y), 0, new Vector3f(1f, 1f, 1f));
		renderer.draw(new Vector2f(gameBoard.getPosition().x + gameBoard.getSize().x, gameBoard.getPosition().y),
				new Vector2f(5, gameBoard.getSize().y), 0, new Vector3f(1f, 1f, 1f));

		if (gameBoard.getSize().x < 1080) {
			gameBoard.getSize().x += 20f;
		}

		if (gameBoard.getPosition().x > 60) {
			gameBoard.getPosition().x -= 10;
		}

		if (gameBoard.getSize().x < 1080) {
			gameBoard.getSize().x += 20f;
			gameBoard.getPosition().x -= 10f;
		} else {
			fontTexturekr = new FontTexture(font, levelData.readyTalk(level));
			fontRenderer.renderString(fontTexturekr, "*" + levelData.readyTalk(level), 80, 380, new Vector3f(1, 1, 1),
					1.7f);
		}
	}

	int selectBtn;

	public void ui(int selectBtn) {
		this.selectBtn = selectBtn;
		renderer.drawSprite(ResourceManager.getTexture("Fight_Button"), new Vector2f(60, 713), new Vector2f(210, 80), 0,
				new Vector3f(1f, 1f, 1f));
		renderer.drawSprite(ResourceManager.getTexture("Attack_Button"), new Vector2f(353, 713), new Vector2f(210, 80),
				0, new Vector3f(1, 1, 1));
		renderer.drawSprite(ResourceManager.getTexture("Item_Button"), new Vector2f(646, 713), new Vector2f(210, 80), 0,
				new Vector3f(1, 1, 1));
		renderer.drawSprite(ResourceManager.getTexture("Mercy_Button"), new Vector2f(939, 713), new Vector2f(210, 80),
				0, new Vector3f(1, 1, 1));
		if (Game.game.GAMESTATUS == GameStatus.TALK || Game.game.GAMESTATUS == GameStatus.GAME) {
			this.selectBtn = 0;
		}
		switch (this.selectBtn) {
		case 1: {
			renderer.drawSprite(ResourceManager.getTexture("Fight_Button_On"), new Vector2f(60, 713),
					new Vector2f(210, 80), 0, new Vector3f(0f, 1f, 1f));
			break;
		}
		case 2: {
			renderer.drawSprite(ResourceManager.getTexture("Attack_Button_On"), new Vector2f(353, 713),
					new Vector2f(210, 80), 0, new Vector3f(0, 1, 1));
			break;
		}
		case 3: {
			renderer.drawSprite(ResourceManager.getTexture("Item_Button_On"), new Vector2f(646, 713),
					new Vector2f(210, 80), 0, new Vector3f(0, 1, 1));
			break;
		}
		case 4: {
			renderer.drawSprite(ResourceManager.getTexture("Mercy_Button_On"), new Vector2f(939, 713),
					new Vector2f(210, 80), 0, new Vector3f(0, 1, 1));
			break;
		}

		}
		drawMuffet();
	}

	public void battleClose() {
		Game.game.GAMESTATUS = GameStatus.GAME.SELECT;
		Game.game.spiders.clear();
		Game.game.boomerang.clear();
		Game.game.donut.clear();
		Game.game.spiders2.clear();
		player.resetPos();
	}
	

	private void opning() {
		renderer.drawSprite(ResourceManager.getTexture("logo"),
				new Vector2f(Game.game.WIDTH / 2 - 400, Game.game.HEIGHT / 2 - 550), new Vector2f(800, 800), 0,
				new Vector3f(1, 1, 1));
		String score;
		score = "                          Record \n\n";
		for(int i = 0; i < Game.game.clearMans.size(); i++) {
			score += Game.game.clearMans.get(i) + "\n";
		}
		fontTexture = new FontTexture(font, score);
		
		fontRenderer.renderString(fontTexture, score, Game.game.WIDTH / 2 - 350, Game.game.HEIGHT / 2 - 60,
				new Vector3f(1, 1, 1), 1.6f);
		
		fontTexturekr = new FontTexture(font, "나가기", "선택", "취소", "이동 : 방향키");
		
		fontRenderer.renderString(fontTexturekr, "ESC : 나가기", Game.game.WIDTH / 2 + 350, 0,
				new Vector3f(1, 1, 1), 1.6f);
		fontRenderer.renderString(fontTexturekr, "Z : 선택", Game.game.WIDTH / 2 + 350, 50,
				new Vector3f(1, 1, 1), 1.6f);
		fontRenderer.renderString(fontTexturekr, "X : 취소", Game.game.WIDTH / 2 + 350, 100,
				new Vector3f(1, 1, 1), 1.6f);
		fontRenderer.renderString(fontTexturekr, "이동 : 방향키", Game.game.WIDTH / 2 + 350, 150,
				new Vector3f(1, 1, 1), 1.6f);
	}

	public void ending(float timeTaken, int damage) {
		fontRenderer.renderString(fontTexturekr, "Timer : " + Float.toString(timeTaken), Game.game.WIDTH / 2 - 210, Game.game.HEIGHT / 2 - 300,
				new Vector3f(1, 1, 1), 3.0f);
		fontRenderer.renderString(fontTexturekr, "Damage : " + Integer.toString(damage), Game.game.WIDTH / 2 - 200, Game.game.HEIGHT / 2 - 200,
				new Vector3f(1, 1, 1), 3.0f);
		fontRenderer.renderString(fontTexture, "CONGRATULATION!", Game.game.WIDTH / 2 - 210, Game.game.HEIGHT / 2,
				new Vector3f(1, 1, 1), 2.0f);
	}
	
	boolean boardMove = false;
//	String nick;
//
//	public void writeNickName(String string) {
//		nick += string;
//	}

	public void render() {
		if (boardMove) {
			boardMovePaturnThread.bugBoardMoveMain();
		}
		if (Game.game.GAMESTATUS == GameStatus.GAME) {
			inBattle();
		} else if (Game.game.GAMESTATUS == GameStatus.SELECT) {
			inSelect();
		} else if (Game.game.GAMESTATUS == GameStatus.ITEM) {
			inItem();
		} else if (Game.game.GAMESTATUS == GameStatus.TALK) {
			inTalk();
		} else if (Game.game.GAMESTATUS == GameStatus.GAMEOVER) {
			opning();
		}

	}

	public boolean isBoardMove() {
		return boardMove;
	}

	public void setBoardMove(boolean boardMove) {
		this.boardMove = boardMove;
	}

	public void textRender() {
		fontRenderer.renderString(fontTexture, "NORMAL", 60, 650, new Vector3f(1, 1, 1), 1.7f);
		fontRenderer.renderString(fontTexture, "LV 1", 270, 650, new Vector3f(1, 1, 1), 1.7f);
		fontRenderer.renderString(fontTexture, "HP " + player.getHp() + "/20", 400, 650, new Vector3f(1, 1, 1), 1.7f);
	}

}
