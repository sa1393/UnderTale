package level;

import gameobject.*;
import undertale.*;
import util.*;

public class PlayerHitThread implements Runnable {
	Player player;
	int damage;
	
	public PlayerHitThread(Player player, int damage) {
		this.player = player;
		this.damage = damage;
	}
	
	@Override
	public void run() {
		Game.game.isHit = true;
		player.setHp(player.getHp() - damage);
		Game.game.damage += this.damage;
		if(player.getHp() <= 0) {
			Game.game.isHit = false;
			Game.game.undertale.level.stopLevelThread();
			Game.game.GAMESTATUS = GameStatus.GAMEOVER;
			Game.game.undertale.reset();
			Game.game.music.close();
		}
		try {
			player.setSpriteTexture(ResourceManager.getTexture("Damage_heart"));
			Thread.sleep(100);
			player.setSpriteTexture(ResourceManager.getTexture("Normal_heart"));
			Thread.sleep(100);
			player.setSpriteTexture(ResourceManager.getTexture("Damage_heart"));
			Thread.sleep(100);
			player.setSpriteTexture(ResourceManager.getTexture("Normal_heart"));
			Thread.sleep(100);
			player.setSpriteTexture(ResourceManager.getTexture("Damage_heart"));
			Thread.sleep(100);
			player.setSpriteTexture(ResourceManager.getTexture("Normal_heart"));
			Thread.sleep(100);
			player.setSpriteTexture(ResourceManager.getTexture("Damage_heart"));
			Thread.sleep(100);
			player.setSpriteTexture(ResourceManager.getTexture("Normal_heart"));
			Thread.sleep(100);
			player.setSpriteTexture(ResourceManager.getTexture("Damage_heart"));
			Thread.sleep(100);
			player.setSpriteTexture(ResourceManager.getTexture("Normal_heart"));
			Thread.sleep(100);
			player.setSpriteTexture(ResourceManager.getTexture("Damage_heart"));
			Thread.sleep(100);
			player.setSpriteTexture(ResourceManager.getTexture("Normal_heart"));
			Game.game.isHit = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
