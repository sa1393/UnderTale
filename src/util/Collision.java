package util;

import gameobject.GameObject;

public class Collision {
	public static boolean checkCol(GameObject one, GameObject two) {
		boolean colX = one.getPosition().x + one.getSize().x >= two.getPosition().x
				&& two.getPosition().x + two.getSize().x >= one.getPosition().x;

		boolean colY = one.getPosition().y + one.getSize().y >= two.getPosition().y
				&& two.getPosition().y + two.getSize().y >= one.getPosition().y;

		return colX && colY;
	}
}
