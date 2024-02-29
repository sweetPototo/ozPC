package pj;


import java.sql.*;

public class UserTimeDAO {	// 데이터 베이스 접근, CRUD 기능
	Connection con;
	PreparedStatement ps;
	ResultSet rs;
	
	String url, user, pass;
	
	public UserTimeDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
		}catch (ClassNotFoundException e) {
			System.err.println("드라이버 검색 실패 !!");
			e.printStackTrace();
		}
		
		url = "jdbc:oracle:thin:@localhost:1521:xe";
		user = "fin01";
		pass = "fin01";
		
	}
		

	
	// 아이디로 시간 가져오기
	public int getTime(String id) {	
		try {
			System.out.println("DAO 전달 id : " + id);
			String sql = "select time from visitor where id = ?";
			con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement(sql);
			ps.setString(1, id);  // 인덱스는 1부터 시작하며, id 값을 설정
			
			rs = ps.executeQuery();
			
            if (rs.next()) {
            	int user_time = rs.getInt("time");
                return user_time;
            } else {
                // 해당 아이디의 레코드가 없을 때 처리
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("getTime 메서드 실행 중 오류 발생");
            return 0;
        }
	}
	
	// 아이디에 남은 시간 저장해 주기
	public void setTime(String id, int time) {
		try {
			String sql = "update visitor set time = ? where id = ?";
			con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement(sql);
			ps.setInt(1, time);
			ps.setString(2, id);
			ps.executeUpdate();
		}catch(SQLException e) {
			System.err.println("insert 메소드 실행 중 오류 발생!!");
			e.printStackTrace();
		}
	}







	



	
		
	}
