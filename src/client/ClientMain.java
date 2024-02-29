//음식, 채팅 클릭 후 창 띄우기 위한거

package client;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ClientMain extends JFrame implements ActionListener{
	
	//먹거리 매출
	private int total = 0;
	
	Exit exit = new Exit();
	
	LocalDate now = LocalDate.now();	
	String str = now.toString();
	
	private Container con;
	private JPanel jp = new JPanel();
	private JButton food_bt = new JButton("음식 주문");
	private JButton chat_bt = new JButton("채팅");
	private JButton open_bt = new JButton("오픈");
	private JButton exit_bt = new JButton("마감");
	
	public void updateTotal(int newTotal) {
		total = newTotal;
	}
	public void init() {
    	con = this.getContentPane();
    	con.setLayout(new BorderLayout());
    	con.add(jp);
    	jp.setLayout(new GridLayout(1,4));
    	jp.add(chat_bt); chat_bt.addActionListener(this);
    	jp.add(food_bt); food_bt.addActionListener(this);
    	jp.add(open_bt); open_bt.addActionListener(this);
    	jp.add(exit_bt); exit_bt.addActionListener(this);
    	
    }
	public ClientMain(String title) {
        super(title);
        this.init();
        super.setSize(800, 500);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int xpos = (int) (screen.getWidth() / 2 - this.getWidth() / 2);
        int ypos = (int) (screen.getHeight() / 2 - this.getHeight() / 2);
        
        this.setLocation(xpos, ypos);
        this.setResizable(false);
        this.setVisible(true);
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == food_bt) {
			FoodGUI foodGUI = new FoodGUI("먹거리주문");
		}else if (e.getSource() == chat_bt) {
			new ChatClientGUI("관리자와 채팅");
		}else if (e.getSource() == open_bt) {
			exit.insertDate(str);
		}else if (e.getSource() == exit_bt) {
			new TotalGUI("매출");
		}
	}
}

