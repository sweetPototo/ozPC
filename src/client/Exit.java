//매출 DB연동
package client;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import pj.ClientMessage;

public class Exit {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs;
	FoodDTO dto;
	
	public Exit() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url,"fin01","fin01");
			
		}catch(Exception e) {
			System.err.println("드라이버 검색 실패!!");
			e.printStackTrace();
		}
	}
	public Connection getCon() {
		return conn;
	}
	
	public int insertDate(String today) {
		try {
			conn = new Exit().getCon();
			String sql = "insert into datetable values(?, 0)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, today);
			int res = pstmt.executeUpdate();
			System.out.println("오픈");
			return res;
		}catch (SQLException e) {
			e.printStackTrace();
			System.out.println("insert 메소드 오류");
			return 0;
		}
	}
	
	public boolean updateMoney (int money, String today) {
		int flag = 0;
		try {
			conn = new Exit().getCon();
			String sql = "update datetable set money=money+? where datecol=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, money);
			pstmt.setString(2, today);
			flag = pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("update 메소드 실패");
		}finally {
			try {
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
			}catch(SQLException e) {}
		}
		if (flag > 0) {
			return true;
		}else {
			return false;
		}
	}
	
	public List<FoodDTO> list() {
		ClientMessage cm = new ClientMessage();
		try {
			conn = new Exit().getCon();
			String sql = "select * from datetable";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			List<FoodDTO> list = new ArrayList<>();
			while (rs.next()) {
				dto = new FoodDTO();
				dto.setTotal(rs.getInt("money"));
				dto.setToday(rs.getString("datecol"));
				list.add(dto);
			}
			return list;
		}catch(SQLException e) {
			System.out.println("exit list 메소드 오류");
			e.printStackTrace();
			return null;
		}
	}	
}
