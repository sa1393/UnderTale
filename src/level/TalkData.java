package level;

public class TalkData implements Runnable{
	public void run() {
		
	}
	
	public TalkData() {

	}
	
	public void patturn(int turn) {
		switch (turn) {
		case 1:
			break;

		default:
			break;
		}
	}
	
	public String talk(int turn) {
		switch (turn) {
		case 1:
			return "왜 그렇게 창백해?\n자부심을 가지라고~";
		case 2:
			return "맛있는 케이크가\n될 거잖아~\n아후후후~";
		case 3:
			return "놓아달라고?\n멍청한 소리\n하지 마~";
		case 4:
			return "네 영혼으로\n모든 거미들이\n행복해질 거야~~~";
		case 5:
			return "내 정신좀 봐!\n애완동물을\n소개해 주는 걸\n잊었네~";
		case 6:
			return "너에 대해\n경고한 사람이\n말이야...";
		case 7:
			return "네 영혼에 많은\n돈을 걸었거든.";
		case 8:
			return "예쁜 미소였지~\n그리고...\n아후후~";
		case 9:
			return "이상하겠지만,\n난 정말 그 사람이\n어둠속에서...형체가\n바뀌는 걸 봤어...?";
		case 10:
			return "점심 시간이네.\n그렇지? 애완동물한테\n먹이 주는 걸 잊었네~";
		case 11:
			return "그 돈이면, 이젠\n거미들이 재결합을\n할 수 있다고~";
		case 12:
			return "못 들어봤어?\n거미들은 몇 세대나\n폐허에 갇혀\n있었다고!";
		case 13:
			return "문을 지나가더라도 ,\n스노우딘의 치명적인\n추위는 버틸 수\n없을 거야.";
		case 14:
			return "하지만 네 영혼에\n걸린 돈이면, 따뜻한\n리무진을 렌트할 수\n있을 거야~";
		case 15:
			return "그럼 남은 돈으론...\n멋진 휴가를 보낼 수 있었겠지~\n아님 거미 야구장을\n짓거나~";
		case 16:
			return "이야기는 여기까지...\n저녁 시간이지?\n아후후후후~";
		case 17:
			return "어머 미안~\n오해가 있었나 보구나";
		default:
			return "버그";
		}
	}
	
	public String readyTalk(int turn) {
		switch (turn) {
		case 1:
			return "머펫이 너를 잡았다!";
		case 2:
			return "모든 거미들이 음악에 맞춰\n박수를 치고 있다!";
		case 3:
			return "머펫은 네 주위로 거미줄을 옭아 매고 있다.";
		case 4:
			return "머펫이 너에게 컵을 던진다.";
		case 5:
			return "모든 거미들이 음악에 맞춰\n박수를 치고 있다!";
		case 6:
			return "머펫은 네에게 거미가 담긴 컵을 던진다.";
		case 7:
			return "머펫이 너에게 컵을 던진다.";
		case 8:
			return "모든 거미들이 음악에 맞춰\n박수를 치고 있다!";
		case 9:
			return "머펫은 네 주위로 거미줄을 옭아 매고 있다.";
		case 10:
			return "머펫은 네에게 거미가 담긴 컵을 던진다.";
		case 11:
			return "모든 거미들이 음악에 맞춰\n박수를 치고 있다!";
		case 12:
			return "머펫은 네 주위로 거미줄을 옭아 매고 있다.";
		case 13:
			return "머펫이 너에게 컵을 던진다.";
		case 14:
			return "모든 거미들이 음악에 맞춰\n박수를 치고 있다!";
		case 15:
			return "머펫은 네 주위로 거미줄을 옭아 매고 있다.";
		case 16:
			return "머펫이 너에게 컵을 던진다.";
		default:
			return "버그";
		}
	}
	
	
}
