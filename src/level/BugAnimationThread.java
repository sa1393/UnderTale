package level;

import gameobject.GameObject;
import util.ResourceManager;

public class BugAnimationThread implements Runnable {
	GameObject bug;
	int bugAniStep = 2;

	public BugAnimationThread(GameObject bug) {
		this.bug = bug;
	}

	@Override
	public void run() {
		try {
			while(true) {
				Thread.sleep(300);
				bugAniStep++;
				if (bugAniStep > 5)
					bugAniStep = 2;
				bug.setSpriteTexture(ResourceManager.getTexture("bug" + Integer.toString(bugAniStep)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
