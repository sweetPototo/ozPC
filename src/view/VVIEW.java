package view;
 

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import javax.swing.JPanel;

import javax.swing.WindowConstants;
 
public class VVIEW extends JFrame  {
	
		 private BufferedImage img = null;
		 private JButton ibt;
		 private JButton obt;
		 private JButton fbt;
		 
		 public VVIEW() {
	 		 
	 	 this.Main();
	 	  ibt.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		Desktop desktop = Desktop.getDesktop();
					try {
					URI uri = new URI("https://www.google.com");
	                desktop.browse(uri);
	                setVisible(true);
	            } catch (IOException | URISyntaxException ex) {
	            	System.out.println("실행오류");
	                ex.printStackTrace();
	            }
	        	}
		 });
	 	  obt.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	new Fgame();
	        	
	        	}
		 });
	 	  fbt.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		new game();
        		
	 }
		 });
		 }
	 	// 생성자
	 	public void Main() {

        setTitle("Oz PC cafe Visitor"); //타이틀
        setSize(1620, 870);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
 
        // 레이아웃 설정
        setLayout(null);
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 1620, 870);
        layeredPane.setLayout(null);
 
        // 패널1 
        MyPanel panel = new MyPanel();
        panel.setBounds(0, 0, 1620, 870);
        layeredPane.add(panel);
        
        // 메인이미지 
        try {
            img = ImageIO.read(new File("img/PCB7.png"));
        } catch (IOException e) {
            System.out.println("이미지 불러오기 실패");
            System.exit(0);
       }
        
        add(panel);
        add(layeredPane);
        layeredPane.add(panel);
        //겜버튼
        fbt = new JButton(new ImageIcon("img/fb1.png"));
        fbt.setBounds(642, 559, 130, 129);
        
        obt = new JButton(new ImageIcon("img/game2.png"));
        obt.setBounds(494, 556, 130, 130);
        
        ibt = new JButton(new ImageIcon("img/inter2.png"));
        ibt.setBounds(340, 556, 134, 134);
        layeredPane.add(ibt);
        layeredPane.add(obt);
        layeredPane.add(fbt);
        //fbt.setBorderPainted(false);
      //  obt.setBorderPainted(false);
       // ibt.setBorderPainted(false);
        /*ibt.addActionListener(this);
        obt.addActionListener(this);
        fbt.addActionListener(this);*/
        
        //화면 항상 중앙에 띄우기
        Dimension frameSize = getSize();
        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((windowSize.width - frameSize.getWidth()) / 2);
        int y = -8; // 이 값은 상단에서 원하는 위치로 조절
        setLocation(x, y);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        
	 	}	
	 	public class MyPanel extends JPanel {
    	public void paint(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }
  }
	
public static void main(String[] args) {
	new VVIEW();
}	
}