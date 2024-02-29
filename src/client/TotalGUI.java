package client;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.*;

public class TotalGUI extends JFrame {
	Exit exit = new Exit();
    private Container con;
	private JTextArea total_ta = new JTextArea();
	
    private DecimalFormat df = new DecimalFormat("###,###");
	
	public void init() {
        con = this.getContentPane();
        con.setLayout(new BorderLayout());

        JScrollPane scroll = new JScrollPane(total_ta);
        con.add(scroll);
        setText();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void setText() {	
		List<FoodDTO> total = exit.list();
		total_ta.append(exit.dto.getToday() + "\t");
		total_ta.append(df.format(exit.dto.getTotal()) + "원" + "\n");
	}
	
	public TotalGUI(String title) {
		super(title);
        this.init();
        super.setSize(500, 300);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int xpos = (int) (screen.getWidth() / 2 - this.getWidth() / 2);
        int ypos = (int) (screen.getHeight() / 2 - this.getHeight() / 2);
        
        this.setLocation(xpos, ypos);
        this.setResizable(false);
        this.setVisible(true);
	}
}
