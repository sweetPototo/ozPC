package client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FoodDAOUpdate {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs;
	
	
	public FoodDAOUpdate() {
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
	
	
	//주문 시 재고 -1 정의
	public boolean update(String name) {
		int flag = 0;	
		try {
			conn = new FoodDAOUpdate().getCon();
			String sql = "update test set stock=stock-1 where MENUNAME=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			flag = pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("update 메소드 실패");
		}finally {
			try {
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
			}catch (SQLException e) {}
		}
		if (flag > 0) {
			return true;
		}else {
			return false;
		}
	}
}