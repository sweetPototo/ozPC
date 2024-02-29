package pj;

import java.util.*;

public class UserTimeProImpl implements UserTimePro {
	UserTimeDAO dao = new UserTimeDAO();
	Scanner in = new Scanner(System.in);
	
	@Override
	
	public int UserLogin(String id) {
		return dao.getTime(id);
		// getTime(id);
		// DB에서 아이디, 남은 시간 값 받아온 후 gui 화면에 띄워 준다
	}

	@Override
	public void UserLogout(String id, int time) {
		dao.setTime(id, time);
	}



}