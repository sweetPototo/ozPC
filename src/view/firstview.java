package view;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.WindowConstants;







public class firstview extends JFrame implements ActionListener {

	JButton mbt = new JButton("관리자");
	JButton vbt = new JButton("방문자");
	BufferedImage img = null;
	JPanel fpan = new JPanel();
	
	public firstview() {
		Selectview();
	}
	public void Selectview() {

        setTitle("Oz PC cafe Select"); //타이틀
        setSize(631, 439);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
 
        // 레이아웃 설정
        setLayout(null);
        JLayeredPane firstPane = new JLayeredPane();
        firstPane.setBounds(0, 0, 631, 439);
        firstPane.setLayout(null);
 
        // 패널1 
        MyPanel panel = new MyPanel();
        panel.setBounds(0, 0, 631, 439);
        firstPane.add(panel);
        add(panel);
        add(firstPane);
        firstPane.add(panel);
        firstPane.add(mbt);
        firstPane.add(vbt);
        // 메인이미지 
        try {
            img = ImageIO.read(new File("img/ozmain.png"));
        } catch (IOException e) {
            System.out.println("이미지 불러오기 실패");
            System.exit(0);
       }
        
        
        mbt.addActionListener(this);
	 // 관리자 / 방문자
    mbt = new JButton(new ImageIcon("img/member1.png"));
    mbt.setBounds(90, 196, 200, 70);
    
    vbt = new JButton(new ImageIcon("img/visitor.png"));
    vbt.setBounds(343, 194, 200, 70);
  
    firstPane.add(mbt);
    firstPane.add(vbt);
    
    //버튼 테두리 안보이기
    mbt.setBorderPainted(false);
    vbt.setBorderPainted(false);
    
    mbt.addActionListener(this);
    
    setVisible(true);
    
    //화면 항상 중앙에 띄우기
    Dimension frameSize = getSize();
    Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
    setLocation((windowSize.width - frameSize.width) / 2,
            (windowSize.height - frameSize.height) / 2); 
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setVisible(true);
 	
 	//관리자 로그인창으로 넘어가기
    	mbt.addActionListener(new ActionListener() {
    	public void actionPerformed(ActionEvent e) {
    	new LoginFrame(); //넘어가~
    	setVisible(false);  // 넘어가고 너는 종료
    }
    	
});
    	vbt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			new LoginFrame2(); //넘어가~
			setVisible(false);  // 넘어가고 너는 종료
				
			}
    		
    	});
	}















	class MyPanel extends JPanel {
    	public void paint(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }
  }



//메인
public static void main(String[] args) {
	new firstview();
	
}
@Override
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	
}


}
