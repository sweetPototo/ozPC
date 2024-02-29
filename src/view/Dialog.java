package view;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;




class Dialog extends JFrame{
	private Container con;
	
	public void init() {
		con = this.getContentPane();  //JFrame 위에 Container를 올린다
		this.setLayout(new BorderLayout());
		
		
	}
	
	public Dialog(String title) {
		super(title);
		this.init();	
		super.setSize(400, 300);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int)(screen.getWidth()/2 - this.getWidth()/2);
		int ypos = (int)(screen.getHeight()/2 - this.getHeight()/2);
		
		this.setLocation(xpos, ypos);
		this.setResizable(false);
		this.setVisible(true);
	}
}

class Menu_Client extends JFrame{
	private Container con;
	
	public void init() {
		con = this.getContentPane();  //JFrame 위에 Container를 올린다
		this.setLayout(new BorderLayout());
		
		
	}
	

	
	public Menu_Client(String title) {
		super(title);
		this.init();	
		super.setSize(400, 300);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int)(screen.getWidth()/2 - this.getWidth()/2);
		int ypos = (int)(screen.getHeight()/2 - this.getHeight()/2);
		
		this.setLocation(xpos, ypos);
		this.setResizable(false);
		this.setVisible(true);
	}
}


