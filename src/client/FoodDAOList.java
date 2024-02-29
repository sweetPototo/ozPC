package client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FoodDAOList {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs;
	
	public FoodDAOList() {
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
	//재고 가져오기 위함
	public int list(String name) {
		try {
			String sql = "select stock from test where MENUNAME=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			List<FoodDTO> list = new ArrayList<>();
			while (rs.next()) {
				FoodDTO dto = new FoodDTO();
				dto.setStock(rs.getInt("STOCK"));
				list.add(dto);
			}
			for (FoodDTO dto : list) {
				return dto.getStock();
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("list메소드 실패");
			return 0;
		}
		return 0;
	}
	
	public List<FoodDTO> listName(String name) {
		try {
			String sql = "select stock from test where MENUNAME=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			List<FoodDTO> list = new ArrayList<>();
			while (rs.next()) {
				FoodDTO dto = new FoodDTO();
				dto.setName(rs.getString("MENUNAME"));
				list.add(dto);
			}
			for (FoodDTO dto : list) {
				return list;
			}
			
		}catch(Exception ee) {
			ee.printStackTrace();
			System.out.println("listName 메소드 실패");
			return null;
		}
		return null;
	}
}
