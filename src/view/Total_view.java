package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import pj.PCDAO;

public class Total_view extends JFrame {
	String[] name = {"매출일자", "매출"};
	DefaultTableModel dt = new DefaultTableModel(name,0);
	
	JTable jt = new JTable(dt);
	JScrollPane jsp = new JScrollPane(jt);
	
	PCDAO dao = new PCDAO();
	
	public void init() {
		this.add(jsp);
		dao.selectAll(dt);
		jt.setRowSelectionInterval(0, 0);
		//테이블 내용 가운데 정렬
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel columnModel = jt.getColumnModel();
		for (int i=0; i<columnModel.getColumnCount(); ++i) {
			columnModel.getColumn(i).setCellRenderer(centerRenderer);
		}
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);		
	}
	
	public Total_view(String title) {
		super(title);
		super.setSize(1000, 900);
		this.init();
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int)(screen.getWidth()/2 - this.getWidth()/2);
		int ypos = (int)(screen.getHeight()/2 - this.getHeight()/2);
		
		this.setLocation(xpos, ypos);
		this.setResizable(false);
		this.setVisible(true);
		
	}

}
