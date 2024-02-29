//클라이언트 채팅창

//클라이언트 나가기 후 다시 입장하면 채팅 안됨
//다른 클라이언트가 입장하면 채팅 안됨
package client;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

public class ChatClientGUI extends JFrame 
					implements KeyListener, ActionListener, Runnable{
	private Container con;
	private JTextArea jta = new JTextArea("");
	private JTextField jtf = new JTextField();
	private JButton input_bt = new JButton("전송");
	private JButton exit_bt = new JButton("나가기");
	private JPanel south_p = new JPanel();
	private JPanel east_p = new JPanel();
	
	//스크롤 만들기
	private JScrollPane scrollPane = new JScrollPane(jta);

	private InetAddress ia;
	private Socket soc;
	private PrintWriter pw;
	private BufferedReader br;
	
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
		/* 채팅창에서 X 누르면 고갱님 화면까지 같이 종료됨 주석처리해둠
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); */
	}
	
	//스크롤 항상 아래로
	public void addLog(String log) {
		jta.append(log + "\n");
		jta.setCaretPosition(jta.getDocument().getLength());
	}
	
	public ChatClientGUI(String title) {
		super(title);
		this.init();	
		super.setSize(400, 300);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int)(screen.getWidth()/2 - this.getWidth()/2);
		int ypos = (int)(screen.getHeight()/2 - this.getHeight()/2);
		
		this.setLocation(xpos, ypos);
		this.setResizable(false);
		this.setVisible(true);
		
		try {
			ia = InetAddress.getByName("localhost");
			soc = new Socket(ia, 12345);
			pw = new PrintWriter(soc.getOutputStream());
			br = new BufferedReader(new InputStreamReader(soc.getInputStream()));
			Thread th = new Thread(this);
			th.start();
			pw.flush();
		}catch(Exception e) {}
	}

	@Override
	public void run() {
		while(true) {
			try {
				String msg = br.readLine();
				addLog("관리자 : " + msg);
			}catch(IOException e) {}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == input_bt) {
			String message = jtf.getText();
			addLog("나 : " + message);
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
			addLog("나 : " + jtf.getText());
			pw.println(jtf.getText());
			pw.flush();
			jtf.setText("");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}
}
