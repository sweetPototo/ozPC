package view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;



	public class Fgame extends JFrame {
		
		private BufferedImage img = null;
		JLayeredPane FgamePane = new JLayeredPane();
		public Fgame() {
			
			
	        setTitle("온라인 게임"); //타이틀
	        setSize(1100, 750);
	        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	 
	        // 레이아웃 설정
	        setLayout(null);
	        
	        FgamePane.setBounds(0, 0, 1100, 750);
	        FgamePane.setLayout(null);
	        
	        // 패널1 
	        MyPanel panel = new MyPanel();
	        panel.setBounds(0, 0, 1100, 750);
	        FgamePane.add(panel);
	        
	        // 이미지 
	        try {
	            img = ImageIO.read(new File("img/fgame1.png"));
	        } catch (IOException e) {
	            System.out.println("이미지 불러오기 실패");
	            System.exit(0);
	       }
	        
	        add(panel);
	        add(FgamePane);
	        FgamePane.add(panel);
	        
	        Dimension frameSize = getSize();
	        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
	        setLocation((windowSize.width - frameSize.width) / 2,
	                (windowSize.height - frameSize.height) / 2); 
	        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	        setVisible(true);
	       
	        
		}
		class MyPanel extends JPanel {
	    	public void paint(Graphics g) {
	        g.drawImage(img, 0, 0, null);
	    }
	  }


	public static void main(String[] args) {
		new Fgame();
	}	
	}


