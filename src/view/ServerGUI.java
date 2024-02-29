package view;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


class OzServerGui extends JFrame implements ActionListener {
	private Container con;
	private JPanel server_menu_p = new JPanel();
	private JPanel server_menu_east_p = new JPanel();
	private JPanel alarm_p = new JPanel();						// 알림 오는 구역
	private JTextArea alarm = new JTextArea();
	private JScrollPane alarm_scroll = new JScrollPane(alarm); 	// 스크롤이 가능하도록 TextArea 만듦
	private JButton menu_client = new JButton("고객 관리");			// 관리 메뉴 버튼들
	private JButton menu_time = new JButton("시간 관리");			// 시간 관리?
	DefaultListModel<String> lm = new DefaultListModel<>();
	JList<String> ls = new JList<>(lm);   
	private static Seat seat[] = new Seat[30];
	private static Panel seat_p = new Panel(); 
	visitordao dao = new visitordao();
	visitorinfo vi;
	public static void set_seat() {
	    for (int i = 0; i < 30; ++i) {
	        seat[i] = new Seat(i, 0); // 각 seat[i]를 Seat 객체로 초기화
	        seat_p.add(seat[i]);
	        seat[i].setHorizontalAlignment(JLabel.CENTER);
	        seat[i].setBorder(new LineBorder(Color.gray));
	    }
	}
	
	public static void set_start_seat(int click_seat, int time) {
	    seat[click_seat].setText("<html><body style='text-align:center;'>사용 중<br/>" + (click_seat + 1) + "번<br/>남은 시간: " + time + "분</body></html>");
	}
	
	public static void ititial_seat(int click_seat) {
	    seat[click_seat].setText(click_seat+1 + "번");
	}
	
	public void init() {
		con = this.getContentPane();  
		this.setLayout(new BorderLayout());
		// 알림창 gui
		this.add("South", alarm_p);
        alarm.setRows(10); // 원하는 행 수
        alarm.setColumns(120); // 원하는 열 수
		alarm_p.add(alarm_scroll);
		
		// 메뉴 버튼
		this.add("North", server_menu_p);
		server_menu_p.setLayout(new BorderLayout());
		server_menu_p.add("East", server_menu_east_p);
		server_menu_east_p.setLayout(new GridLayout(1,2));
		server_menu_east_p.add(menu_client);	menu_client.addActionListener(this);
		server_menu_east_p.add(menu_time);		menu_time.addActionListener(this);
		
		// 좌석
		this.add("Center", seat_p);	
		seat_p.setLayout(new GridLayout(6,5,200,5));
		
		set_seat();
		
		// this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	

	
	public OzServerGui(String title) {
		super(title);
		this.init();	
		super.setSize(1500, 900);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int)(screen.getWidth()/2 - this.getWidth()/2);
		int ypos = (int)(screen.getHeight()/2 - this.getHeight()/2);
		
		this.setLocation(xpos, ypos);
		this.setResizable(false);
		this.setVisible(true);
	}


	@Override
	
	public void actionPerformed(ActionEvent e) {
		if (menu_client == e.getSource()) {
			visitorinfo frame = new visitorinfo();
		
			//List<UserDTO2> list = dao.listTest();
			//for(UserDTO2 UserDTO2 : list) {
		
		    //  }  
		}
		else if (menu_time == e.getSource()) {
			Time_Dialog td = new Time_Dialog("시간 추가");
		}
	
}
public static class ServerGUI {
	public static void main(String[] args) {
		OzServerGui frame = new OzServerGui("관리자 화면");
	}
}
}