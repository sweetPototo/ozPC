//클라이언트와 연결 되는지 그냥 만든거

package client;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import pj.ClientMessage;

public class ChatServerGUI extends JFrame implements KeyListener, ActionListener, Runnable{
	private Container con;
	private JTextArea jta = new JTextArea("");
	private JTextField jtf = new JTextField();
	private JButton input_bt = new JButton("전송");
	private JButton exit_bt = new JButton("나가기");
	private JPanel south_p = new JPanel();
	private JPanel east_p = new JPanel();
	
	
	//스크롤 만들기
	private JScrollPane scrollPane = new JScrollPane(jta);
	
	
	private boolean View = true;
	
	private ServerSocket ss;
	private Socket soc;
	private ObjectOutputStream oos;
	private String id;
	
	public ChatServerGUI(String title, Socket soc, String id) {
		super(title);
		this.soc = soc;
		this.id = id;
		this.init();
		
		super.setSize(400, 300);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int)(screen.getWidth()/2 - this.getWidth()/2);
		int ypos = (int)(screen.getHeight()/2 - this.getHeight()/2);
		
		this.setLocation(xpos, ypos);
		this.setResizable(false);
		this.setVisible(true);		
	}
	
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
		jta.append(log);
		jta.setCaretPosition(jta.getDocument().getLength());
	}
	
	// 클라이언트 메시지 들어왔을 때
	public void clientMessage(String id, String message) {
		addLog(id + "님 : " + message+ "\n");
		toFront();
	}
	
	
	public void sendChat(String id, String message) {
		
    	try {
    		oos = new ObjectOutputStream(new BufferedOutputStream((soc.getOutputStream())));
			ClientMessage cm = new ClientMessage();
			cm.setSendChat(message);
			cm.setSendType("chat");
			cm.disp();
			
			try {
				oos.writeObject(cm);
				oos.flush();
			}catch(IOException e) {
				e.printStackTrace();
			}
			System.out.println(id + " 님에게 메시지 전송 : " + message+ "\n");
		

		}catch(Exception e) {
			e.printStackTrace();
		}
    }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == input_bt) {
			String message = jtf.getText();
			addLog("관리자 : " + message + "\n");
			sendChat(id, message);
			jtf.setText("");
		}else if (e.getSource() == exit_bt) {
			this.setVisible(false);
		}
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			String message = jtf.getText();
			addLog("관리자 : " + message + "\n");
			sendChat(id, message);
			jtf.setText("");
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
 

