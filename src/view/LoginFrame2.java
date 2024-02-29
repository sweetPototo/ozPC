package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import pj.ClientGUI;

import client.ClientMain;

public class LoginFrame2 extends JFrame implements ActionListener {
	
		 private BufferedImage img = null;
		 private JTextField loginTextField;
		 private JPasswordField passwordField;
		 private JButton bt;
		 private JButton jbt;
		 Userinfo users;
		 visitordao dao;
		 join2 jo;
		 OzServerGui sg;
		 VVIEW vi;
		 public static String id;
		 LocalDate now = LocalDate.now();
		 String str = now.toString();
		 public LoginFrame2() {
	 		 
	 	 this.Main();
		 users = new Userinfo();
		 dao = new visitordao();
		
		 }
	 

	 	// 생성자
	 	public void Main() {

        setTitle("Oz PC cafe visitor Login"); //타이틀
        setSize(631, 439);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
 
        // 레이아웃 설정
        setLayout(null);
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 631, 439);
        layeredPane.setLayout(null);
 
        // 패널1 
        MyPanel panel = new MyPanel();
        panel.setBounds(0, 0, 631, 439);
        layeredPane.add(panel);
        
        // 메인이미지 
        try {
            img = ImageIO.read(new File("img/ozis.png"));
        } catch (IOException e) {
            System.out.println("이미지 불러오기 실패");
            System.exit(0);
       }
        
        // 로그인 
        loginTextField = new JTextField(15);
        loginTextField.setBounds(248, 163, 100, 24);
        layeredPane.add(loginTextField); 
        loginTextField.setForeground(Color.blue);
        loginTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        
        // 패스워드
        passwordField = new JPasswordField(15);
        passwordField.setBounds(248, 209, 100, 24);
        passwordField.setForeground(Color.blue);
        passwordField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        layeredPane.add(passwordField);
 
        // 로그인, 회원가입 버튼 추가
        bt = new JButton(new ImageIcon("img/login1.png"));
        bt.setBounds(121, 326, 153, 32);
        
        jbt = new JButton(new ImageIcon("img/join.png"));
        jbt.setBounds(326, 324, 132, 40);
      
        layeredPane.add(bt);
        layeredPane.add(jbt);
        
        bt.addActionListener(this);
        
        //  추가
        add(panel);
        add(layeredPane);
        layeredPane.add(panel);
        
        //화면 항상 중앙에 띄우기
        Dimension frameSize = getSize();
        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((windowSize.width - frameSize.width) / 2,
                (windowSize.height - frameSize.height) / 2); 
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
	 	
	 	//회원가입창으로 넘어가줘~
        	jbt.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	new join2(); //넘어가~
        	setVisible(false);  // 회원가입창으로 넘어가고 너는 종료
        }
        	
    });
	 	}
    
       
    @Override
		public void actionPerformed(ActionEvent e) {
    		id = loginTextField.getText();
    		char[] pass = passwordField.getPassword();
			String pwd = new String(pass);
			
			if(id.equals("") || pwd.equals("")) {
			//빈칸불가 메시지
			 JOptionPane.showMessageDialog(null, "빈칸불가");
	 } 		else if(id != null && pwd != null) {
	 		if(dao.userLogin1(id, pwd)) {	//이 부분이 데이터베이스에 접속해 로그인 정보를 확인하는 부분
	 			//id -> 클라이언트로 전송 
	 		 JOptionPane.showMessageDialog(null, "환영합니다 고갱님");
	 		//방문객 화면 
	 		 new VVIEW();
	 		 ClientGUI frame = new ClientGUI("고갱님 환영합니다");
	 		 dao.insertDate(str);
	 		 dispose();
	 	 }
			else {
			//로그인 실패
			JOptionPane.showMessageDialog(null, "로그인 ^X^");
	 	}   
     } 		
		
    }
    
	
        

    	class MyPanel extends JPanel {
    	public void paint(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }
  }
 
    	public Object getUsers() {
		
		return null;
	}

public static void main(String[] args) {
	new LoginFrame2();
}	
}
        