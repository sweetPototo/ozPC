package view;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;


import java.io.*;

public class OzServerGui extends JFrame implements ActionListener {
	private Container con;
	private JPanel server_menu_p = new JPanel();
	private JPanel server_menu_east_p = new JPanel();
	private JPanel alarm_p = new JPanel();						// 알림 오는 구역
	static JTextArea alarm = new JTextArea("");
	private JScrollPane alarm_scroll = new JScrollPane(alarm); 	// 스크롤이 가능하도록 TextArea 만듦
	private JButton menu_time = new JButton("시간 관리");			// 시간 관리?
	private JButton server_start = new JButton("고객 관리");		// 관리 메뉴 버튼들
	private JButton total_money = new JButton("매출 관리");

	
	private static Seat seat[] = new Seat[30];
	private static Panel seat_p = new Panel(); 
	
	public static void set_seat() {
	    for (int i = 0; i < 30; ++i) {
	        seat[i] = new Seat(i, 0); // 각 seat[i]를 Seat 객체로 초기화
	        seat_p.add(seat[i]);
	        seat[i].setHorizontalAlignment(JLabel.CENTER);
	        seat[i].setBorder(new LineBorder(Color.gray));
	    }
	}
	
	// 로그인 하면 뜨는 메시지
	public void addMessageToAlarm(String message) {
		
		alarm.append(message);
		alarm.repaint();
		System.out.println(alarm.getText());
		alarm.setCaretPosition(alarm.getDocument().getLength());
		
	}
	
	public static void set_start_seat(int click_seat, int time) {
	    seat[click_seat].setText("<html><body stye='text-align:center;'>사용 중<br/>" + (click_seat+1) + "번<br/>남은 시간: " + (time/60) + "분</body></html>");
	}
	
	public static void ititial_seat(int click_seat) {
	    seat[click_seat].setText((click_seat+1) + "번");
	}
	

	
	public void init() {
		con = this.getContentPane();  
		con.setLayout(new BorderLayout());
		// 알림창 gui
		con.add("South", alarm_p);
		alarm_p.setLayout(new BorderLayout());
		alarm.setText("Oz 피시방 서버에 오신 것을 환영합니다\n");
        alarm.setFont(new Font("", Font.BOLD, 15));
		alarm.setRows(10); // 원하는 행 수
        alarm.setColumns(120); // 원하는 열 수
		alarm_p.add(alarm_scroll);
		
		// 메뉴 버튼
		con.add("North", server_menu_p);
		server_menu_p.setLayout(new BorderLayout());
		server_menu_p.add("East", server_menu_east_p);
		server_menu_east_p.setLayout(new GridLayout(1,3));
		server_menu_east_p.add(menu_time);		menu_time.addActionListener(this);
		server_menu_east_p.add(server_start);	server_start.addActionListener(this);
		server_menu_east_p.add(total_money);	total_money.addActionListener(this);


		
		// 좌석
		con.add("Center", seat_p);	
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
	    if (server_start == e.getSource()) {
	    	visitor_view vv = new visitor_view("고객 관리");
	    } else if (menu_time == e.getSource()) {
	        Time_Dialog td = new Time_Dialog("시간 추가");
	    } else if (total_money == e.getSource()) {
	    	Total_view tv = new Total_view("매출 관리");
	    }
	}
	

	
}
