package view;
import java.util.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import client.FoodGUI;

	public class join2 extends JFrame /*implements ViewPro*/ {
		LoginFrame owner;
		Userinfo users;
		UserDTO2 user;
		visitordao dao; 
		
		
		//버튼
		JButton join = new JButton("회원가입");
		JButton cancel = new JButton("취소");
		//텍스트필드
		JTextField id = new JTextField(15);
		JPasswordField pwd1 = new JPasswordField(15);
		JPasswordField pwd2 = new JPasswordField(15);
		JTextField birth = new JTextField(15);
		JTextField name = new JTextField(15);
		JTextField phone = new JTextField(15);
		JTextField email = new JTextField(15);
		//패널
		JPanel TotalPanel = new JPanel();
		
		
		public join2()	{	
				addAct1();
				Blank();
				init();
			dao = new visitordao();
		}
		//구성
		public void init()	{
		//패널만들기		
		JPanel idPanel = new JPanel();
		idPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		idPanel.add(new JLabel("아이디 : "));
		idPanel.add(id);
			
		JPanel pwdPanel = new JPanel();
		pwdPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		pwdPanel.add(new JLabel("비밀번호 : "));
		pwdPanel.add(pwd1);
			
		JPanel pwd1Panel = new JPanel();
		pwd1Panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		pwd1Panel.add(new JLabel("비밀번호확인 : "));
		pwd1Panel.add(pwd2);
			
			
		JPanel namePanel = new JPanel();
		namePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		namePanel.add(new JLabel("이    름 : "));
		namePanel.add(name);
		
			
		JPanel birthPanel = new JPanel();
		birthPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		birthPanel.add(new JLabel("생년월일 : "));
		birthPanel.add(birth);
			
			
		JPanel phonePanel = new JPanel();
		phonePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		phonePanel.add(new JLabel("전화번호 : "));
		phonePanel.add(phone);
			
		JPanel emailPanel = new JPanel();
		emailPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		emailPanel.add(new JLabel("E-mail : "));
		emailPanel.add(email);
			
			
		TotalPanel.setLayout(new GridLayout(8, 2));
		TotalPanel.add(idPanel);
		TotalPanel.add(pwdPanel);
		TotalPanel.add(pwd1Panel);
		TotalPanel.add(namePanel);
		TotalPanel.add(birthPanel);
		TotalPanel.add(phonePanel);
		TotalPanel.add(emailPanel);
			
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new FlowLayout());
		contentPanel.add(TotalPanel);
		//회원가입, 취소버튼
		JPanel panel = new JPanel();
		panel.add(join);
		panel.add(cancel);
		add(panel, BorderLayout.SOUTH);
		add(contentPanel, BorderLayout.CENTER);
		add(panel, BorderLayout.SOUTH);
		setBounds(200, 200, 300, 300);
		setVisible(true);
		//크기 돈따취~
		setResizable(false);
		
		setTitle("Oz pc cafe 회원가입"); // 타이틀
		JPanel jPanel = new JPanel();
		jPanel.setBackground(null);
		setSize(350,320);
			
		add(jPanel);
		//화면 중앙에 띄우기
		Dimension frameSize = getSize();
		Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
	    setLocation((windowSize.width - frameSize.width) / 2,
	            (windowSize.height - frameSize.height) / 2); 
	    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	    setVisible(true);
	}
			
		//이벤트모음 
	    public void addAct1()  {
		//회원가입 버튼 
		join.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {

	
		//정보 하나라도 비어있으면
	    if(Blank()) {
	        JOptionPane.showMessageDialog(join2.this, "빈칸없이 입력해주세요." );
	        return;
	        } 
	       
	        // 비밀번호와 비번확인이 일치하지 않았을 때
	    if(!String.valueOf(pwd1.getPassword()).equals(String.valueOf(pwd2.getPassword()))) {
	        		JOptionPane.showMessageDialog(join2.this, "비밀번호와 비밀번호확인이 일치하지 않습니다." );
	        		pwd1.requestFocus();
	   }else {
	            
	            user = new UserDTO2(id.getText(), String.valueOf(pwd1.getPassword()), String.valueOf(pwd2.getPassword()),
	            		name.getText(), birth.getText(), phone.getText(), email.getText());

	    int result = dao.userInsert1(user.getId(), user.getPwd(), user.getName(), 
	            		user.getBirth(), user.getPhone(), user.getEmail());

	     if (result > 0) {
	                JOptionPane.showMessageDialog(null, "회원가입에 성공했습니다");
	                new LoginFrame2();
	                dispose(); 
	            }
	        
		 else {
				System.out.println("회원가입 실패");
				JOptionPane.showMessageDialog(null, "회원가입에 실패하였습니다");
				new join2();
        		dispose();
			}
	        }
	      
		}
		}	        		
 
	);
	
		//취소 버튼 
		cancel.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
					new LoginFrame2();
					dispose();
	}
});
	}
	    //빈칸일때
	    public boolean Blank() {
		   boolean result = false;
		   if(id.getText().isEmpty()) {
	        	id.requestFocus();
	        	return true;
}
		   if(String.valueOf(pwd1.getPassword()).isEmpty()) {
			    pwd1.requestFocus();
	        	return true;
}
		   if(String.valueOf(pwd2.getPassword()).isEmpty()) {
	        	pwd2.requestFocus();
	        	return true;
}
	       if(name.getText().isEmpty()) {
	        	name.requestFocus();
	        	return true;
	}
	       if(birth.getText().isEmpty()) {
	        	birth.requestFocus();
	        	return true;
	   }
	       if(phone.getText().isEmpty()) {
	        	phone.requestFocus();
	        	return true;
	    	}
	       if(String.valueOf(email.getText()).isEmpty()) {
	        	email.requestFocus();
	        	return true;
	 	}
	        	return result;
	    }
        
 }