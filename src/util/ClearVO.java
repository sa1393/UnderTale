package util;

public class ClearVO {
	int HP;
	float timer;
	
	public int getHP() {
		return HP;
	}
	public void setHP(int hP) {
		HP = hP;
	}
	public float getTimer() {
		return timer;
	}
	public void setTimer(float timer) {
		this.timer = timer;
	}
	
	@Override
	public String toString() {
		return "시간 : " + timer + "초" + "                   잃은 체력 : " + HP;
	}
}
