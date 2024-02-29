package view;

import java.awt.*;
import javax.swing.*;



public class visitorinfo extends JFrame {
	
	private Container con;
	private JPanel list_p = new JPanel();						// 알림 오는 구역
	private JTextArea list_a = new JTextArea();
	
	private UserDTO2 dto = new UserDTO2();
	private visitordao dao = new visitordao();
	
	
	public visitorinfo() {
		
		setTitle("회원관리");
		this.init();	
		super.setSize(1100, 700);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int)(screen.getWidth()/2 - this.getWidth()/2);
		int ypos = (int)(screen.getHeight()/2 - this.getHeight()/2);
		
		this.setLocation(xpos, ypos);
		this.setResizable(false);
		this.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void init() {
		
		con = this.getContentPane();  
		con.setLayout(new BorderLayout());
		con.add(list_p);
		list_p.add(list_a);
		JScrollPane scroll = new JScrollPane(list_a);
		con.add(scroll);
		list_a.setRows(33); // 원하는 행 수
        list_a.setColumns(120); // 원하는 열 수
        setText();
        
   }  
		
	public void setText() {
		
		list_a.setText(dto.getId());
		list_a.setText(dto.getPwd());
		list_a.setText(dto.getName());
		list_a.setText(dto.getBirth());
		list_a.setText(dto.getPhone());
		list_a.setText(dto.getEmail()); 
		
		dto.setId("                    ");
        dto.setPwd("                    ");
        dto.setName("                    ");
        dto.setBirth("                    ");
        dto.setPhone("                    ");
        dto.setEmail("                    ");
        // JTextArea(list_a)에 데이터 추가
        list_a.append("ID:     " + dto.getId() + "\t");
        list_a.append("Password:      " + dto.getPwd() + "\t");
        list_a.append("Name:      " + dto.getName() + "\t");
        list_a.append("Birth:      " + dto.getBirth() + "\t");
        list_a.append("Phone:      " + dto.getPhone() + "\t");
        list_a.append("Email:      " + dto.getEmail() + "\t");

		

}

	public static void main(String[] args) {
	new visitorinfo();

}	
}