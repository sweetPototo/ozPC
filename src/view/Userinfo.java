package view;
import java.util.ArrayList;

public class Userinfo {
	private ArrayList<UserDTO> users;
	
	public Userinfo() {
		users = new ArrayList<UserDTO>();
	}
	
	// 회원 추가
	public void addUsers(UserDTO user) {
	        users.add(user);
	}
	// 아이디 중복 확인
    public boolean isIdOverlap(String id) {
    	return users.contains(new UserDTO(id));
    }
    // 회원 삭제
	public void withdraw(String id) {
       users.remove(getUser(id));
    }
	// 유저 정보 가져오기
	public UserDTO getUser(String id) {
		return users.get(users.indexOf(new UserDTO(id)));
	}
	// 회원인지 아닌지 확인
	public boolean contains(UserDTO user) {
		return users.contains(user);
	}
	

}