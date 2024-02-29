package pj;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.*;
import view.LoginFrame2;
import client.*;

//중앙 고객 main GUI <- 이곳에서 id 받아야 함
public class ClientGUI extends JFrame implements ActionListener{
	LocalDate now = LocalDate.now();
	static ClientGUI cg;
	static TDialog td;
	static ChatClientGUI ccg;
	static PCDAO dao;
	static Client cl;
	static FoodGUI fg;
	
	String str = now.toString();
	int hour, minute;
	
	public String id = LoginFrame2.id;  //로그인 화면과 연동
	
	private Container con;
	private JPanel center_p = new JPanel();
	private JPanel center_south_p = new JPanel();
			JButton eat_b = new JButton();
			JButton timeReload_b = new JButton("");
			JButton chat_b = new JButton("");
	private JPanel center_center_p = new JPanel();
	private JLabel timeRemaining_l = new JLabel("00 : 00", JLabel.CENTER);
	private JPanel center_center_west_p = new JPanel();
	private JLabel seat_l = new JLabel("OZPC", JLabel.CENTER);
	private JPanel center_center_west_center_p = new JPanel();
	private JLabel id_l = new JLabel("ID : 00.000", JLabel.CENTER);
			JButton logout_b = new JButton("로그아웃");
	private JPanel popuralgame_p = new JPanel();
			JButton popuralgame_b = new JButton();
			JButton popuralgame_b2 = new JButton();
			JButton popuralgame_b3 = new JButton();
	private InetAddress ia;
	private Socket soc;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	//private PrintWriter pw;
			
	//중앙 고객 main GUI 구현
	public void init() {
		con = this.getContentPane();
		con.setLayout(new BorderLayout());
		//내가 다시 만들엇지롱
		con.add("South", popuralgame_p);
		popuralgame_p.setLayout(new GridLayout(1,3));
		popuralgame_b = new JButton(new ImageIcon("img/LOL3.png"));
		popuralgame_b2 = new JButton(new ImageIcon("img/FIFA43.png"));
		popuralgame_b3 = new JButton(new ImageIcon("img/MAPLE5.png"));
		popuralgame_p.add(popuralgame_b);
		popuralgame_p.add(popuralgame_b2);
		popuralgame_p.add(popuralgame_b3);
		popuralgame_p.setPreferredSize(new Dimension(500,50));
		con.add(center_p);
		center_p.setLayout(new BorderLayout());
		
		center_p.add("South", center_south_p);
		center_south_p.setLayout(new GridLayout(1,3));
		
		eat_b = new JButton(new ImageIcon("img/order2.png"));
		center_south_p.add(eat_b);
		eat_b.addActionListener(this);
		
		timeReload_b = new JButton(new ImageIcon("img/time1.png"));
		center_south_p.add(timeReload_b);
		timeReload_b.addActionListener(this);
		
		chat_b = new JButton(new ImageIcon("img/chat1.png"));
		center_south_p.add(chat_b);
		chat_b.addActionListener(this);
		center_south_p.setPreferredSize(new Dimension(420,60));
		
		center_p.add(center_center_p);
		center_center_p.setLayout(new BorderLayout());
		timeRemaining_l.setFont(new Font("", Font.BOLD, 55));
		timeRemaining_l.setText(hour + ":" + minute);
		center_center_p.add(timeRemaining_l);
		
		center_center_p.add("West", center_center_west_p);
		center_center_west_p.setLayout(new GridLayout(2,1));
		seat_l.setFont(new Font("", Font.BOLD, 25));
		center_center_west_p.add("North", seat_l);
		center_center_west_p.setPreferredSize(new Dimension(135,60));
		
		center_center_west_p.add(center_center_west_center_p);
		center_center_west_center_p.setLayout(new GridLayout(2,1));
		id_l.setFont(new Font("", Font.PLAIN, 15));
		id_l.setText(id);
		center_center_west_center_p.add(id_l);		//로그인 화면으로부터 ID 받아오기
		logout_b = new JButton(new ImageIcon("img/logout1.png"));
		center_center_west_center_p.add(logout_b);
		logout_b.addActionListener(this);
		center_center_west_center_p.setPreferredSize(new Dimension(100,100));
		
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	}
	
	public ClientGUI(String title) {
		super(title);
		System.out.println("client ()");
		try {
			ia = InetAddress.getByName("localhost");
			soc = new Socket(ia, 24511);
			
			
			System.out.println("서버 연결 성공");
		}catch(Exception e) {
			 e.printStackTrace();
		}
		System.out.println("socket stream()");
		dao = new PCDAO();
		System.out.println("PCDAO()");
		dao.selectLogintPc(id);
		cl = new Client();
		System.out.println("Client()");
		init();
		System.out.println("init()");
		td = new TDialog(this, "요금결제", false);	//요금결제 Dialog 객체화
		ccg = new ChatClientGUI(this, "채팅", false);//채팅 Dialog 객체화
		fg = new FoodGUI(this, "먹거리 주문", false);
		super.setSize(500, 250);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int)(screen.getWidth() - this.getWidth());
		int ypos = 0;
		this.setLocation(xpos, ypos);
		this.setResizable(false);
		this.setVisible(true);
	}
			
	//남은시간 타이머 구현
	public void timer(int dbtime) {
		hour = dbtime/3600;
		minute = (dbtime%3600)/60;
		if(minute <= 9) {
			timeRemaining_l.setText(String.valueOf(hour) + ":0" + String.valueOf(minute));
		}else {
			timeRemaining_l.setText(String.valueOf(hour) + ":" + String.valueOf(minute));
		}
	}
	//중앙 고객 main GUI 이벤트 처리
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == eat_b) {			//먹거리 구매
			fg.isview(true);
		}
		if(e.getSource() == timeReload_b) {		//요금 구매
			td.isview(true);
		}
		if(e.getSource() == chat_b) {			//채팅
			ccg.isview(true);
		}
		if(e.getSource() == logout_b) {			//로그아웃
			cl.sendClientID(id, "f", "logout");
			try {
				dao.con.close();
				soc.close();
				System.exit(0);
			} catch (SQLException e1) {
				e1.printStackTrace();
				System.out.println("con.close 실패!");
			} catch (IOException e1) {
				e1.printStackTrace();
				System.out.println("soc.close 실패!");
			}
		}
			popuralgame_b.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Desktop desktop = Desktop.getDesktop();
		            try {
		                URI uri = new URI("https://www.leagueoflegends.com/ko-kr/");
		                desktop.browse(uri);
		            } catch (IOException ex) {
		                ex.printStackTrace();
		            } catch (URISyntaxException ex) {
		                ex.printStackTrace();
		            }
		}
	});
			popuralgame_b2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Desktop desktop = Desktop.getDesktop();
		            try {
		                URI uri = new URI("https://fconline.nexon.com/main/index");
		                desktop.browse(uri);
		            } catch (IOException ex) {
		                ex.printStackTrace();
		            } catch (URISyntaxException ex) {
		                ex.printStackTrace();
		            }
		}
	});
			popuralgame_b3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Desktop desktop = Desktop.getDesktop();
		            try {
		                URI uri = new URI("https://maplestory.nexon.com/Home/Main");
		                desktop.browse(uri);
		            } catch (IOException ex) {
		                ex.printStackTrace();
		            } catch (URISyntaxException ex) {
		                ex.printStackTrace();
		            }
		}
	});
}
	//TCP통신용
	class Client extends Thread{
		//Client 생성
		public Client() {
			sendClientID(id, "t", "login");
			this.start();
		}
		//server에게 로그인 정보 넘기기
		public void sendClientID(String id, String loginox, String type) {
			try {
				dao.login(id, loginox);
				ClientMessage cm = dao.selectLogintPc(id);
				cm.setSendType(type);
				cm.setSendChat("0");
				oos = new ObjectOutputStream(soc.getOutputStream());
				oos.writeObject(cm);
				cm.disp();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		//server에게 보낼 송신용 객체 세팅 -> 전송 (먹거리, 시간, 채팅)
		public void sendClientBuy(String id, int sumTime, String menuList, String chat, String type) {
			ClientMessage cm = new ClientMessage();
			cm = dao.selectLogintPc(id);
			cm.setSendAddTime(sumTime);
			cm.setSendMenuList(menuList);
			cm.setSendType(type);
			cm.setSendChat(chat);
			try {
				oos = new ObjectOutputStream(soc.getOutputStream());
				oos.writeObject(cm);
				System.out.println("ttt:");
				cm.disp();
				oos.flush();
			}catch(IOException e) {
				e.printStackTrace();}
			System.out.println("[서버로 보낼 cm]" + "\n");
			cm.disp();
		}
		//server로부터 객체 전달받아 type확인
		public void run() {
			while(true) {
				try {
					ois = new ObjectInputStream(soc.getInputStream());
					Object obj = ois.readObject();
					ClientMessage cm = (ClientMessage)obj;
					System.out.println("[서버로부터 전달받음]");
					cm.disp();
					if(cm.getSendType().equals("Time")) {
						timer(cm.getSendAddTime());
					}else {
						ccg.memberMessageView(cm.getSendChat(), true);
					}
				}catch(IOException e) {}
				 catch (ClassNotFoundException e) {}
			}
		}
	}
	
	//요금충전 GUI
	class TDialog extends JDialog implements ActionListener{
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		private int xpos = (int)(screen.getWidth() - 1300);
		private int ypos = (int)(screen.getHeight() -(screen.getHeight() - 250));
		private String timeStr = "1시간(1,000원) 2시간(2,000원) 3시간(3,000원) 4시간(4,000원) 10시간(10,000원) 20시간(20,000원) 30시간(30,000원) 40시간(40,000원)";
			    String timeStrArr[] = timeStr.split(" ");
			    
	    private int sumTimePrice = 0;	//총 결제 금액
		private int sumTime = 0;
		
		private DecimalFormat df = new DecimalFormat("###,###");
		
		private BorderLayout bl = new BorderLayout();
		private Container con = new Container();
				JButton time_b[] = new JButton[8];
		private JPanel time_p = new JPanel();
				TextArea ta = new TextArea();
		private JLabel sum_l = new JLabel("총 결제금액 : ", JLabel.LEFT);
				JButton pay_b = new JButton("결제");
		private JButton cancle_b = new JButton("취소");
		private JPanel east_p = new JPanel();
		private JPanel east_south_p = new JPanel();
		
		//요금충전 GUI 구현
		public void init() {
			this.add(con);
			con.setLayout(new BorderLayout());
			con.add(time_p);
			time_p.setLayout(new GridLayout(4,2,0,50));
			
			for(int i=0; i<timeStrArr.length; ++i) {
				time_b[i] = new JButton();
				time_b[i].setText(timeStrArr[i]);
				time_p.add(time_b[i]);
				time_b[i].addActionListener(this);
			}
			con.add("East", east_p);
			east_p.setPreferredSize(new Dimension(300,800));
			east_p.setLayout(new BorderLayout());
			east_p.add(ta);
			east_p.add("South", east_south_p);
			east_south_p.setPreferredSize(new Dimension(300,150));
			east_south_p.setLayout(new GridLayout(3,1));
			sum_l.setFont(new Font("", Font.BOLD, 15));
			east_south_p.add(sum_l);
			east_south_p.add(cancle_b);
			cancle_b.addActionListener(this);
			east_south_p.add(pay_b);
			pay_b.addActionListener(this);
		}
		public TDialog(JFrame owner, String title, boolean modal) {
			super(owner, title, modal);
			this.init();
			super.setSize(800, 800);
			this.setLocation(xpos, ypos);
			this.setResizable(false);
		}
		//요금충전 GUI 창 보임 여부
		public void isview(boolean b) {
			this.setVisible(b);
		}
		
		//요금충전 GUI 이벤트 처리
		@Override
		public void actionPerformed(ActionEvent e) {
			//요금제 버튼 이벤트 처리
			for(int i=0; i<8; ++i) {
				if(e.getSource() == time_b[i]) {
					int sumTimePriceDB = 0;	//DB에서 끌고온 금액
					sumTimePriceDB = dao.setPrice(id); //DB에서 금액 끌고 오기
					ta.append(time_b[i].getText() + "\n");
					//System.out.println(i);
					sumTime += dao.buttonTime(time_b[i].getText());  //DB로부터 각 버튼별 시간 가져오기
					sumTimePriceDB += dao.buttonPrice(time_b[i].getText()); //DB로부터 각 버튼별 요금 가져오기
					sumTimePrice += dao.buttonPrice(time_b[i].getText());
					System.out.println(time_b[i].getText());
					sum_l.setText("총 결제금액 : " + df.format(sumTimePrice) + "원");
				}
			}
			//요금 결제 버튼 이벤트 처리
			if(e.getSource() == pay_b) {
				String taText = ta.getText();
				if(!taText.equals("")) {//공백이 아니면
					int res1 = dao.buyTimePricePC(sumTimePrice, id);	//결제한 요금 DB와 연동 처리 메소드(로그인시 아이디 받아오기)
					//System.out.println("str : " + str);
					boolean res2 = dao.updateMoney(sumTimePrice, str);
					//System.out.println("(TDialog.event처리 res1 : " + res1);
					//System.out.println("(TDialog.event처리 res2 : " + res2);
					if(res1 <= 0) {
						JOptionPane.showMessageDialog(this, "결제가 완료되지 않았습니다.");
					}else if(res1 > 0){
						JOptionPane.showMessageDialog(this, "결제되었습니다.");
						isview(false);
						ta.setText("");
						sum_l.setText("총 결제금액 : ");
						//System.out.println("전송할 시간 : " + sumTime);
						//System.out.println("(TDialog)cl : " + cl);
						cl.sendClientBuy(id,sumTime,"0","0", "Time");
						sumTimePrice = 0;
						sumTime = 0;
						//System.out.println("현재 sumTime값 : " + sumTime);
					}
				}else {
					JOptionPane.showMessageDialog(this, "결제하실 요금이 선택되지 않았습니다.");
				}
			}else if(e.getSource() == cancle_b) {
				ta.setText("");
				sum_l.setText("총 결제금액 : ");
				sumTimePrice = 0;
				sumTime = 0;
			}
		}
	}
public class ChatClientGUI extends JDialog implements Serializable, KeyListener, ActionListener{
	private Container con;
	private JTextArea jta = new JTextArea("");
	private JTextField jtf = new JTextField();
	private JButton input_bt = new JButton("전송");
	private JButton exit_bt = new JButton("나가기");
	private JPanel south_p = new JPanel();
	private JPanel east_p = new JPanel();
	
	private boolean View = true;
	
	//스크롤 만들기
	private JScrollPane scrollPane = new JScrollPane(jta);
	
	public void init() {
		con = this.getContentPane();
		con.setLayout(new BorderLayout());
		con.add("Center", scrollPane);
		jta.setEditable(false);
		con.add("South", south_p);
		south_p.setLayout(new BorderLayout());
		south_p.add("Center", jtf);
		south_p.add("East", east_p);
		east_p.setLayout(new GridLayout(1,2));
		east_p.add(input_bt);
		east_p.add(exit_bt);	
		jtf.addKeyListener(this);
		input_bt.addActionListener(this);
		exit_bt.addActionListener(this);
	}

	//스크롤 항상 아래로
	public void addLog(String log) {
		jta.append(log + "\n");
		jta.setCaretPosition(jta.getDocument().getLength());
	}

	public ChatClientGUI(JFrame owner, String title, boolean modal) {
		super(owner, title, modal);
		this.init();	
		super.setSize(400, 300);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int)(screen.getWidth()/2 - this.getWidth()/2);
		int ypos = (int)(screen.getHeight()/2 - this.getHeight()/2);
		
		this.setLocation(xpos, ypos);
		this.setResizable(false);
		this.setVisible(false);
		/*
		try {
			Thread th = new Thread(this);
			th.start();
		}catch(Exception e) {}
		
	}

	@Override
	public void run() {
		while(true) {
			try {
				String msg = br.readLine();
				addLog("관리자 : " + msg + "n");
			}catch(IOException e) {}
		}
		*/
	}
	//관리자의 메세지 화면에 띄우기
	public void memberMessageView(String memberMessage, boolean ox) {
		boolean viewox = ox;
		while(viewox) {
			addLog("관리자 : " + memberMessage);
			viewox = false;
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == input_bt) {
			String message = jtf.getText();
			addLog("나 : " + message);
			cl.sendClientBuy(id, 0, "0", message, "chat");
			jtf.setText("");
		}else if (e.getSource() == exit_bt) {
			dispose();
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			String message = jtf.getText();
			addLog("나 : " + message);
			cl.sendClientBuy(id, 0, "0", message, "chat");
			jtf.setText("");
		}
	}
	//chat GUI 창 보임 여부
	public void isview(boolean b) {
		this.setVisible(b);
	}
	@Override
	public void keyReleased(KeyEvent e) {}
}
	class Food extends JButton {
	    private ImageIcon imgIcon; // 이미지 아이콘을 사용
	    private String name;
	    private int price;
	    private int stock;
	    
	    //이미지 패널에 들어갈 이미지, 이름, 가격
	    public Food (ImageIcon imgIcon, String name, int price, int stock) {
	    	this.imgIcon = imgIcon;
	        this.name = name;
	        this.price = price;
	        this.stock = stock;
	        setIcon(imgIcon); // 아이콘을 버튼에 설정
	        setLayout(new BorderLayout()); // 이미지 아이콘을 가운데 정렬하기 위한 레이아웃 설정
	        add(new JLabel(name, JLabel.CENTER), BorderLayout.NORTH); // 버튼 위에 메뉴 이름 표시
	        add(new JLabel(price + "원", JLabel.CENTER), BorderLayout.SOUTH); // 버튼 아래에 가격 표시
	    }
	    public Food(ImageIcon imgIcon, String name, int price) {
	        this.imgIcon = imgIcon;
	        this.name = name;
	        this.price = price;
	        setIcon(imgIcon); // 아이콘을 버튼에 설정
	        setLayout(new BorderLayout()); // 이미지 아이콘을 가운데 정렬하기 위한 레이아웃 설정
	        add(new JLabel(name, JLabel.CENTER), BorderLayout.NORTH); // 버튼 위에 메뉴 이름 표시
	        add(new JLabel(price + "원", JLabel.CENTER), BorderLayout.SOUTH); // 버튼 아래에 가격 표시
	    }  
	    public String getName() {
	        return name;
	    }    
	    public int getPrice() {
	        return price;
	    }
	    public int getStock(String name) {
	    	return stock;
	    }
	    public void setStock(int newStock) {
	    	stock = newStock;
	    }
	}
	//처음 이미지 넣기 위한 클래스
	class mainFood extends Canvas {
		private Image img;
		public mainFood(Image img) {
			this.img = img;
		}
		public void setImage(Image img) {
			this.img = img;
		}
		public void paint(Graphics g) {
			g.drawImage(img, 0,0,this.getWidth(), this.getHeight(), this);
		} 
	}

	public class FoodGUI extends JDialog implements ActionListener, Runnable{

		private String menu_n = "";
		private int total;
		
		public int getTotal() {
			return total;
		}
		
	    private Container con;
	    
	    private JPanel north_jp = new JPanel(); //인기, 라면류, 밥류 등 메뉴 나누기
	    private JPanel center_jp = new JPanel(); //메뉴 사진 넣기
	    private JPanel east_jp = new JPanel(); //주문내역서 넣기
	    private JPanel mini_jp = new JPanel(); //주문과 취소 버튼 넣기
	    
	    //인기, 라면, 밥, 사이드 등 이미지 버튼 패널
	    private JPanel noodlePanel = new JPanel();
	    private JPanel ricePanel = new JPanel();
	    private JPanel snackPanel = new JPanel();
	    private JPanel coffeePanel = new JPanel();
	    private JPanel sidePanel = new JPanel();
	    private JPanel bestPanel = new JPanel();
	    private JPanel addPanel = new JPanel();
	    
	    //첫 화면     
	    private Image img = Toolkit.getDefaultToolkit().getImage("img/pcfood.png");
	    private mainFood mf = new mainFood(img);

	    //패널 전환 하기 위함
	    private CardLayout cards = new CardLayout();

	    //버튼들 생성
	    private JButton best_bt = new JButton("인기");
	    private JButton noodle_bt = new JButton("라면류");
	    private JButton rice_bt = new JButton("밥류");
	    private JButton snack_bt = new JButton("과자류");
	    private JButton coffee_bt = new JButton("음료류");
	    private JButton side_bt = new JButton("사이드류");
	    private JButton add_bt = new JButton("추가");
	    
	    //이미지 버튼 패널들
	    private Food[] bestFoods = new Food[9];
	    private Food[] noodleFoods = new Food[9];
	    private Food[] riceFoods = new Food[6];
	    private Food[] snackFoods = new Food[6];
	    private Food[] sideFoods = new Food[9];
	    private Food[] addFoods = new Food[4];
	    private Food[] coffeeFoods = new Food[8];
	    
	    //주문, 취소, 나가기 버튼
	    private JButton money_bt = new JButton("현금");
	    private JButton card_bt = new JButton("카드");
	    private JButton cancle_bt = new JButton("취소");
	    private JButton exit_bt = new JButton("나가기");
	    
	    //주문 내역
	    private JTextArea menu_ta = new JTextArea();
	    
	    //주문 메뉴 담을 리스트
	    private ArrayList<Food> list = new ArrayList<>();
	  
	    //가격 3자리씩 , 넣기
	    private DecimalFormat df = new DecimalFormat("###,###");
	    
	    public void init() {
	        con = this.getContentPane();
	        getContentPane().setLayout(cards);
	        con.setLayout(new BorderLayout());
	        con.add("North", north_jp);
	        con.add("Center", center_jp);
	        con.add("East", east_jp);
	        
	        //위쪽에 버튼 넣기
	        north_jp.setLayout(new GridLayout(1, 7));
	        north_jp.add(best_bt);
	        best_bt.addActionListener(this);
	        north_jp.add(noodle_bt);
	        noodle_bt.addActionListener(this);
	        north_jp.add(rice_bt);
	        rice_bt.addActionListener(this);
	        north_jp.add(snack_bt);
	        snack_bt.addActionListener(this);
	        north_jp.add(coffee_bt);
	        coffee_bt.addActionListener(this);
	        north_jp.add(side_bt);
	        side_bt.addActionListener(this);
	        north_jp.add(add_bt);
	        add_bt.addActionListener(this);     
	        
	        //cardsLayout 사용하기 위해 미리 설정
	        center_jp.setLayout(cards);
	        
	        //처음 이미지 넣기
	        center_jp.add(mf);
	        
	        //이미지 버튼 패널 넣어두기
	        center_jp.add(bestPanel, "best");
	        center_jp.add(noodlePanel, "noodle");
	        center_jp.add(ricePanel, "rice");
	        center_jp.add(addPanel, "add");
	        center_jp.add(coffeePanel, "coffee");
	        center_jp.add(sidePanel, "side");
	        center_jp.add(snackPanel, "snack");
	        
	        //패널들 설정
	        noodlePanel.setLayout(new GridLayout(3,3));
	        ricePanel.setLayout(new GridLayout(3,3));
	        bestPanel.setLayout(new GridLayout(3,3));
	        snackPanel.setLayout(new GridLayout(3,3));
	        sidePanel.setLayout(new GridLayout(3,3));
	        addPanel.setLayout(new GridLayout(3,3));
	        coffeePanel.setLayout(new GridLayout(3,3));
	        
	        //버튼에 이미지, 이름, 값 넣기
	        String coffee_n = "아아,아이스티,얼음컵,웰치스 포도,칠성사이다,칸타타라떼,펩시,핫아메리카노";
		    String coffee_p = "1500,1500,700,1800,1800,2000,1800,1500";
			String[] c_name = coffee_n.split(",");
	        String[] c_price = coffee_p.split(",");
	        for (int i = 0; i < coffeeFoods.length; ++i) { //i<coffeeFoods.length으로 이후에 바꾸기
	        	if (dao.list(c_name[i]) == 0) {
	            	ImageIcon imgIcon = new ImageIcon("src/품절.jpg");
	            	coffeeFoods[i] = new Food(imgIcon, c_name[i], Integer.parseInt(c_price[i]));
	            	coffeeFoods[i].setEnabled(false);
	            }else {
	            	ImageIcon imgIcon = new ImageIcon("src/" + c_name[i] + ".jpg");
	            	coffeeFoods[i] = new Food(imgIcon, c_name[i], Integer.parseInt(c_price[i]));
	            	coffeeFoods[i].addActionListener(this);
	            }
	            coffeePanel.add(coffeeFoods[i]);
	        	coffeeFoods[i].repaint();
	        }  
	        String side_n = "감자튀김,군만두,김말이,닭꼬치,소세지,순살치킨,자유시간,콜팝,핫도그"; 
		    String side_p = "2500,2500,2500,3000,3000,3500,700,2500,3000";
			String[] si_name = side_n.split(",");
	        String[] si_price = side_p.split(",");
	        for (int i = 0; i < sideFoods.length; ++i) {
	        	if (dao.list(si_name[i]) == 0) {
	            	ImageIcon imgIcon = new ImageIcon("src/품절.jpg");
	                sideFoods[i] = new Food(imgIcon, si_name[i], Integer.parseInt(si_price[i]));
	                sideFoods[i].setEnabled(false);
	            }else {
	            	ImageIcon imgIcon = new ImageIcon("src/" + si_name[i] + ".jpg");
	            	sideFoods[i] = new Food(imgIcon, si_name[i], Integer.parseInt(si_price[i]));
	            	sideFoods[i].addActionListener(this);
	            }
	            sidePanel.add(sideFoods[i]);
	        	sideFoods[i].repaint();
	        }        
	        String best_n = "핫도그,치킨마요덮밥,신라면,짜파구리,감자튀김,순살치킨,김치볶음밥,소세지,비빔면세트";
		    String best_p = "3000,4800,2000,2700,2500,3500,4500,3000,3000";
			String[] b_name = best_n.split(",");
	        String[] p_price = best_p.split(",");
	        for (int i = 0; i < bestFoods.length; ++i) {
	            if (dao.list(b_name[i]) == 0) {
	            	ImageIcon imgIcon = new ImageIcon("src/품절.jpg");
	                bestFoods[i] = new Food(imgIcon, b_name[i], Integer.parseInt(p_price[i]));
	                bestFoods[i].setEnabled(false);
	            }else {
	            	ImageIcon imgIcon = new ImageIcon("src/" + b_name[i] + ".jpg");
	                bestFoods[i] = new Food(imgIcon, b_name[i], Integer.parseInt(p_price[i]));
	                bestFoods[i].addActionListener(this);
	            }
	            bestPanel.add(bestFoods[i]);
	            bestFoods[i].repaint();
	        }        
	        String noodle_n = "신라면,너구리,불닭볶음면,만두라면,비빔면세트,짜계치,짜파게티,짜파구리,진라면";
		    String noodle_p = "2000,2000,2500,2500,3000,3500,2300,2700,2000";
			String[] n_name = noodle_n.split(",");
	        String[] n_price = noodle_p.split(",");
	        for (int i = 0; i < noodleFoods.length; ++i) {
	        	if (dao.list(n_name[i]) == 0) {
	            	ImageIcon imgIcon = new ImageIcon("src/품절.jpg");
	                noodleFoods[i] = new Food(imgIcon, n_name[i], Integer.parseInt(n_price[i]));
	                noodleFoods[i].setEnabled(false);
	            }else {
	            	ImageIcon imgIcon = new ImageIcon("src/" + n_name[i] + ".jpg");
	            	noodleFoods[i] = new Food(imgIcon, n_name[i], Integer.parseInt(n_price[i]));
	            	noodleFoods[i].addActionListener(this);
	            }
	            noodlePanel.add(noodleFoods[i]);
	        	noodleFoods[i].repaint();
	        }      
	        String rice_n = "김치볶음밥,바베큐덮밥,제육덮밥,치킨마요덮밥,참치마요덮밥,간장계란밥";
		    String rice_p = "4500,5000,5000,4800,4800,4000";
			String[] r_name = rice_n.split(",");
	        String[] r_price = rice_p.split(",");
	        for (int i = 0; i < riceFoods.length; ++i) {
	        	if (dao.list(r_name[i]) == 0) {
	            	ImageIcon imgIcon = new ImageIcon("src/품절.jpg");
	                riceFoods[i] = new Food(imgIcon, r_name[i], Integer.parseInt(r_price[i]));
	                riceFoods[i].setEnabled(false);
	            }else {
	            	ImageIcon imgIcon = new ImageIcon("src/" + r_name[i] + ".jpg");
	            	riceFoods[i] = new Food(imgIcon, r_name[i], Integer.parseInt(r_price[i]));
	            	riceFoods[i].addActionListener(this);
	            }
	        	ricePanel.add(riceFoods[i]);
	        	riceFoods[i].repaint();
	        }  
	        String snack_n = "썬칩,오징어집,포카칩,프링글스,홈런볼,스윙칩"; 
		    String snack_p = "1500,1200,1800,2500,2500,1800";
			String[] s_name = snack_n.split(",");
	        String[] s_price = snack_p.split(",");
	        for (int i = 0; i < snackFoods.length; ++i) {
	        	if (dao.list(s_name[i]) == 0) {
	            	ImageIcon imgIcon = new ImageIcon("src/품절.jpg");
	            	snackFoods[i] = new Food(imgIcon, s_name[i], Integer.parseInt(s_price[i]));
	            	snackFoods[i].setEnabled(false);
	            }else {
	            	ImageIcon imgIcon = new ImageIcon("src/" + s_name[i] + ".jpg");
	            	snackFoods[i] = new Food(imgIcon, s_name[i], Integer.parseInt(s_price[i]));
	            	snackFoods[i].addActionListener(this);
	            }
	            snackPanel.add(snackFoods[i]);
	            snackFoods[i].repaint();
	        }      
	        String add_n = "계란후라이,공기밥,계란,치즈";
		    String add_p = "500,1000,300,500";
			String[] a_name = add_n.split(",");
	        String[] a_price = add_p.split(",");
	        for (int i = 0; i < addFoods.length; ++i) {
	        	if (dao.list(a_name[i]) == 0) {
	            	ImageIcon imgIcon = new ImageIcon("src/품절.jpg");
	            	addFoods[i] = new Food(imgIcon, a_name[i], Integer.parseInt(a_price[i]));
	            	addFoods[i].setEnabled(false);
	            }else {
	            	ImageIcon imgIcon = new ImageIcon("src/" + a_name[i] + ".jpg");
	            	addFoods[i] = new Food(imgIcon, a_name[i], Integer.parseInt(a_price[i]));
	                addFoods[i].addActionListener(this);
	            }
	            addPanel.add(addFoods[i]);
	            addFoods[i].repaint();
	        }  
	        
	        //주문내역서와 주문, 취소 버튼
	        east_jp.setLayout(new BorderLayout());
	        mini_jp.setLayout(new GridLayout(1, 4));
	        setTextArea();
	        east_jp.setPreferredSize(new Dimension(300, 300));
	        east_jp.add("Center", menu_ta);
	        east_jp.add("South", mini_jp);
	        mini_jp.add(money_bt);
	        money_bt.addActionListener(this);
	        mini_jp.add(card_bt);
	        card_bt.addActionListener(this);
	        mini_jp.add(cancle_bt);
	        cancle_bt.addActionListener(this);
	        mini_jp.add(exit_bt);
	        exit_bt.addActionListener(this);
	    }

	    //주문내역 넣기
	    public void setTextArea() {
	        menu_ta.setText("\t           주문내역서");
	        menu_ta.append("\n===========================================");
	        total = 0;
	        for (Food fd : list) {
	            menu_ta.append("\n" + fd.getName() + " \t\t" + df.format(fd.getPrice()) + "원");
	            total += fd.getPrice();
	        }
	        if (total > 0) {
	            menu_ta.append("\n===========================================");
	            menu_ta.append("\n총금액 \t\t" + df.format(total) + "원");
	        }
	    }
	    
	    //주문, 취소시 담은 메뉴 정리
	    public void clear() {
	        menu_ta.setText("\t           주문내역서");
	        menu_ta.append("\n===========================================");
	        list.clear();
	        total = 0;
	    }
	    
	    public FoodGUI(JFrame owner, String title, boolean modal) {
	        super(owner, title, modal);
	        this.init();
	        super.setSize(1300, 800);
	        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	        int xpos = (int) (screen.getWidth() / 2 - this.getWidth() / 2);
	        int ypos = (int) (screen.getHeight() / 2 - this.getHeight() / 2);
	        
	        this.setLocation(xpos, ypos);
	        this.setResizable(false);
	        this.setVisible(false);
	    }
	    //FoodGUI 창 보임 여부
	  	public void isview(boolean b) {
	  		this.setVisible(b);
	  	}
	    
	    @Override
	    public void actionPerformed(ActionEvent e) {
	    	//버튼 클릭시 분류된 메뉴 이미지 패널로 전환
	    	if (e.getSource() == best_bt) {
	    		cards.show(center_jp, "best");
	    	}else if (e.getSource() == noodle_bt) {
	    		cards.show(center_jp, "noodle");
	    	}else if (e.getSource() == rice_bt) {
	    		cards.show(center_jp, "rice");
	    	}else if (e.getSource() == snack_bt) {
	    		cards.show(center_jp, "snack");
	    	}else if (e.getSource() == coffee_bt) {
	    		cards.show(center_jp, "coffee");
	    	}else if (e.getSource() == add_bt) {
	    		cards.show(center_jp, "add");
	    	}else if (e.getSource() == side_bt) {
	    		cards.show(center_jp, "side");
	    	}
	    	//현금이나 카드 결제
	    	if (e.getSource() == money_bt || e.getSource() == card_bt) { 
	    		//menu_n = menu_n.substring(7);
	    		for (Food food : list) {
	    			String menuName = food.getName(); 
	    			menu_n += " + " + menuName;
	    			if (dao.list(menuName) > 0) {
	    				if (dao.update(menuName)) {
	    				}else {
	    					JOptionPane.showMessageDialog(this, menuName + "이/가 주문 실패했습니다." );
	    			}
	    			//재고 부족일 경우
	    			}else {
	    				JOptionPane.showMessageDialog(this, menuName + "은/는 품절입니다.");
	    			}
	    		}
	    		dao.updateMoney(total, str);
	    		JOptionPane.showMessageDialog(this, "주문이 완료됐습니다.");
	    		//ClientMessage.sendMeniList = menu_n;
	    		String sendString = menu_n;
	    		cl.sendClientBuy(id, 0, sendString,"0", "Food");
	    		list.clear();
	    		menu_n = "";
	    		clear();
	    	}
	    	//취소 버튼
	    	else if (e.getSource() == cancle_bt) {
	    		list.clear();
	    		clear();
	    	}
	    	//나가기 버튼
	    	else if (e.getSource() == exit_bt) {
	    		list.clear();
	    		dispose();
	    	}
	    	//버튼 클릭시 주문서에 이름, 가격 넣기
	    	for (Food[] foods : new Food[][]{bestFoods, noodleFoods, riceFoods, snackFoods, sideFoods, addFoods, coffeeFoods}) {    	
	    		for (int i = 0; i < foods.length; i++) {
	                if (foods[i] == e.getSource()) {
	                    list.add(foods[i]);
	                    setTextArea();
	                    break;
	                }
	    		}
			}
	}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
	}
	public static void main (String[] args) {
		cg = new ClientGUI("OzPC CAFE");
	}
}