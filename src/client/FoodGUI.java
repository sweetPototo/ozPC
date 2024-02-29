package client;

import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import javax.swing.*;

import client.FoodDTO;
import client.FoodDAOUpdate;
import pj.ClientMessage;
//메뉴버튼을 클릭시 이름과 가격이 주문내역서에 들어가기 위해 이미지, 이름, 가격 정의
class Food extends JButton {
    private ImageIcon imgIcon; // 이미지 아이콘을 사용
    private String name;
    private int price;
    private int stock;
    
    //이미지 패널에 들어갈 이미지, 이름, 가격
    public Food (ImageIcon imgIcon, String name, int price, int stock) {
    	this.imgIcon = imgIcon;
        this.name = name;
        this.price = price;
        this.stock = stock;
        setIcon(imgIcon); // 아이콘을 버튼에 설정
        setLayout(new BorderLayout()); // 이미지 아이콘을 가운데 정렬하기 위한 레이아웃 설정
        add(new JLabel(name, JLabel.CENTER), BorderLayout.NORTH); // 버튼 위에 메뉴 이름 표시
        add(new JLabel(price + "원", JLabel.CENTER), BorderLayout.SOUTH); // 버튼 아래에 가격 표시
    }
    public Food(ImageIcon imgIcon, String name, int price) {
        this.imgIcon = imgIcon;
        this.name = name;
        this.price = price;
        setIcon(imgIcon); // 아이콘을 버튼에 설정
        setLayout(new BorderLayout()); // 이미지 아이콘을 가운데 정렬하기 위한 레이아웃 설정
        add(new JLabel(name, JLabel.CENTER), BorderLayout.NORTH); // 버튼 위에 메뉴 이름 표시
        add(new JLabel(price + "원", JLabel.CENTER), BorderLayout.SOUTH); // 버튼 아래에 가격 표시
    }  
    public String getName() {
        return name;
    }    
    public int getPrice() {
        return price;
    }
    public int getStock(String name) {
    	return stock;
    }
    public void setStock(int newStock) {
    	stock = newStock;
    }
}
//처음 이미지 넣기 위한 클래스
class mainFood extends Canvas {
	private Image img;
	public mainFood(Image img) {
		this.img = img;
	}
	public void setImage(Image img) {
		this.img = img;
	}
	public void paint(Graphics g) {
		g.drawImage(img, 0,0,this.getWidth(), this.getHeight(), this);
	} 
}

public class FoodGUI extends JFrame implements ActionListener{
	FoodDAOUpdate dao = new FoodDAOUpdate();
	FoodDAOList daoList = new FoodDAOList();
	FoodDTO dto = new FoodDTO();
	Exit exit = new Exit();
	ClientMessage cm = new ClientMessage();

	private String menu_n = "";
	
	private int total;
	
	public int getTotal() {
		return total;
	}
	
	LocalDate now = LocalDate.now();	
	String str = now.toString();
	
    private Container con;
    
    private JPanel north_jp = new JPanel(); //인기, 라면류, 밥류 등 메뉴 나누기
    private JPanel center_jp = new JPanel(); //메뉴 사진 넣기
    private JPanel east_jp = new JPanel(); //주문내역서 넣기
    private JPanel mini_jp = new JPanel(); //주문과 취소 버튼 넣기
    
    //인기, 라면, 밥, 사이드 등 이미지 버튼 패널
    private JPanel noodlePanel = new JPanel();
    private JPanel ricePanel = new JPanel();
    private JPanel snackPanel = new JPanel();
    private JPanel coffeePanel = new JPanel();
    private JPanel sidePanel = new JPanel();
    private JPanel bestPanel = new JPanel();
    private JPanel addPanel = new JPanel();
    
    //첫 화면     
    private Image img = Toolkit.getDefaultToolkit().getImage("src/pcfood.jpg");
    private mainFood mf = new mainFood(img);

    //패널 전환 하기 위함
    private CardLayout cards = new CardLayout();

    //버튼들 생성
    private JButton best_bt = new JButton("인기");
    private JButton noodle_bt = new JButton("라면류");
    private JButton rice_bt = new JButton("밥류");
    private JButton snack_bt = new JButton("과자류");
    private JButton coffee_bt = new JButton("음료류");
    private JButton side_bt = new JButton("사이드류");
    private JButton add_bt = new JButton("추가");
    
    //이미지 버튼 패널들
    private Food[] bestFoods = new Food[9];
    private Food[] noodleFoods = new Food[9];
    private Food[] riceFoods = new Food[6];
    private Food[] snackFoods = new Food[6];
    private Food[] sideFoods = new Food[9];
    private Food[] addFoods = new Food[4];
    private Food[] coffeeFoods = new Food[8];
    
    //주문, 취소, 나가기 버튼
    private JButton money_bt = new JButton("현금");
    private JButton card_bt = new JButton("카드");
    private JButton cancle_bt = new JButton("취소");
    private JButton exit_bt = new JButton("나가기");
    
    //주문 내역
    private JTextArea menu_ta = new JTextArea();
    
    //주문 메뉴 담을 리스트
    private ArrayList<Food> list = new ArrayList<>();
  
    //가격 3자리씩 , 넣기
    private DecimalFormat df = new DecimalFormat("###,###");
    
    public void init() {
        con = this.getContentPane();
        getContentPane().setLayout(cards);
        con.setLayout(new BorderLayout());
        con.add("North", north_jp);
        con.add("Center", center_jp);
        con.add("East", east_jp);
        
        //위쪽에 버튼 넣기
        north_jp.setLayout(new GridLayout(1, 7));
        north_jp.add(best_bt);
        best_bt.addActionListener(this);
        north_jp.add(noodle_bt);
        noodle_bt.addActionListener(this);
        north_jp.add(rice_bt);
        rice_bt.addActionListener(this);
        north_jp.add(snack_bt);
        snack_bt.addActionListener(this);
        north_jp.add(coffee_bt);
        coffee_bt.addActionListener(this);
        north_jp.add(side_bt);
        side_bt.addActionListener(this);
        north_jp.add(add_bt);
        add_bt.addActionListener(this);     
        
        //cardsLayout 사용하기 위해 미리 설정
        center_jp.setLayout(cards);
        
        //처음 이미지 넣기
        center_jp.add(mf);
        
        //이미지 버튼 패널 넣어두기
        center_jp.add(bestPanel, "best");
        center_jp.add(noodlePanel, "noodle");
        center_jp.add(ricePanel, "rice");
        center_jp.add(addPanel, "add");
        center_jp.add(coffeePanel, "coffee");
        center_jp.add(sidePanel, "side");
        center_jp.add(snackPanel, "snack");
        
        //패널들 설정
        noodlePanel.setLayout(new GridLayout(3,3));
        ricePanel.setLayout(new GridLayout(3,3));
        bestPanel.setLayout(new GridLayout(3,3));
        snackPanel.setLayout(new GridLayout(3,3));
        sidePanel.setLayout(new GridLayout(3,3));
        addPanel.setLayout(new GridLayout(3,3));
        coffeePanel.setLayout(new GridLayout(3,3));
        
        //버튼에 이미지, 이름, 값 넣기
        String coffee_n = "아아,아이스티,얼음컵,웰치스 포도,칠성사이다,칸타타라떼,펩시,핫아메리카노";
	    String coffee_p = "1500,1500,700,1800,1800,2000,1800,1500";
		String[] c_name = coffee_n.split(",");
        String[] c_price = coffee_p.split(",");
        for (int i = 0; i < coffeeFoods.length; ++i) {
        	if (daoList.list(c_name[i]) == 0) {
            	ImageIcon imgIcon = new ImageIcon("src/품절.jpg");
            	coffeeFoods[i] = new Food(imgIcon, c_name[i], Integer.parseInt(c_price[i]));
            	coffeeFoods[i].setEnabled(false);
            }else {
            	ImageIcon imgIcon = new ImageIcon("src/" + c_name[i] + ".jpg");
            	coffeeFoods[i] = new Food(imgIcon, c_name[i], Integer.parseInt(c_price[i]));
            	coffeeFoods[i].addActionListener(this);
            }
            coffeePanel.add(coffeeFoods[i]);
        	coffeeFoods[i].repaint();
        }  
        String side_n = "감자튀김,군만두,김말이,닭꼬치,소세지,순살치킨,자유시간,콜팝,핫도그"; 
	    String side_p = "2500,2500,2500,3000,3000,3500,700,2500,3000";
		String[] si_name = side_n.split(",");
        String[] si_price = side_p.split(",");
        for (int i = 0; i < sideFoods.length; ++i) {
        	if (daoList.list(si_name[i]) == 0) {
            	ImageIcon imgIcon = new ImageIcon("src/품절.jpg");
                sideFoods[i] = new Food(imgIcon, si_name[i], Integer.parseInt(si_price[i]));
                sideFoods[i].setEnabled(false);
            }else {
            	ImageIcon imgIcon = new ImageIcon("src/" + si_name[i] + ".jpg");
            	sideFoods[i] = new Food(imgIcon, si_name[i], Integer.parseInt(si_price[i]));
            	sideFoods[i].addActionListener(this);
            }
            sidePanel.add(sideFoods[i]);
        	sideFoods[i].repaint();
        }        
        String best_n = "핫도그,치킨마요덮밥,신라면,짜파구리,감자튀김,순살치킨,김치볶음밥,소세지,비빔면세트";
	    String best_p = "3000,4800,2000,2700,2500,3500,4500,3000,3000";
		String[] b_name = best_n.split(",");
        String[] p_price = best_p.split(",");
        for (int i = 0; i < bestFoods.length; ++i) {
            if (daoList.list(b_name[i]) == 0) {
            	ImageIcon imgIcon = new ImageIcon("src/품절.jpg");
                bestFoods[i] = new Food(imgIcon, b_name[i], Integer.parseInt(p_price[i]));
                bestFoods[i].setEnabled(false);
            }else {
            	ImageIcon imgIcon = new ImageIcon("src/" + b_name[i] + ".jpg");
                bestFoods[i] = new Food(imgIcon, b_name[i], Integer.parseInt(p_price[i]));
                bestFoods[i].addActionListener(this);
            }
            bestPanel.add(bestFoods[i]);
            bestFoods[i].repaint();
        }        
        String noodle_n = "신라면,너구리,불닭볶음면,만두라면,비빔면세트,짜계치,짜파게티,짜파구리,진라면";
	    String noodle_p = "2000,2000,2500,2500,3000,3500,2300,2700,2000";
		String[] n_name = noodle_n.split(",");
        String[] n_price = noodle_p.split(",");
        for (int i = 0; i < noodleFoods.length; ++i) {
        	if (daoList.list(n_name[i]) == 0) {
            	ImageIcon imgIcon = new ImageIcon("src/품절.jpg");
                noodleFoods[i] = new Food(imgIcon, n_name[i], Integer.parseInt(n_price[i]));
                noodleFoods[i].setEnabled(false);
            }else {
            	ImageIcon imgIcon = new ImageIcon("src/" + n_name[i] + ".jpg");
            	noodleFoods[i] = new Food(imgIcon, n_name[i], Integer.parseInt(n_price[i]));
            	noodleFoods[i].addActionListener(this);
            }
            noodlePanel.add(noodleFoods[i]);
        	noodleFoods[i].repaint();
        }      
        String rice_n = "김치볶음밥,바베큐덮밥,제육덮밥,치킨마요덮밥,참치마요덮밥,간장계란밥";
	    String rice_p = "4500,5000,5000,4800,4800,4000";
		String[] r_name = rice_n.split(",");
        String[] r_price = rice_p.split(",");
        for (int i = 0; i < riceFoods.length; ++i) {
        	if (daoList.list(r_name[i]) == 0) {
            	ImageIcon imgIcon = new ImageIcon("src/품절.jpg");
                riceFoods[i] = new Food(imgIcon, r_name[i], Integer.parseInt(r_price[i]));
                riceFoods[i].setEnabled(false);
            }else {
            	ImageIcon imgIcon = new ImageIcon("src/" + r_name[i] + ".jpg");
            	riceFoods[i] = new Food(imgIcon, r_name[i], Integer.parseInt(r_price[i]));
            	riceFoods[i].addActionListener(this);
            }
        	ricePanel.add(riceFoods[i]);
        	riceFoods[i].repaint();
        }  
        String snack_n = "썬칩,오징어집,포카칩,프링글스,홈런볼,스윙칩"; 
	    String snack_p = "1500,1200,1800,2500,2500,1800";
		String[] s_name = snack_n.split(",");
        String[] s_price = snack_p.split(",");
        for (int i = 0; i < snackFoods.length; ++i) {
        	if (daoList.list(s_name[i]) == 0) {
            	ImageIcon imgIcon = new ImageIcon("src/품절.jpg");
            	snackFoods[i] = new Food(imgIcon, s_name[i], Integer.parseInt(s_price[i]));
            	snackFoods[i].setEnabled(false);
            }else {
            	ImageIcon imgIcon = new ImageIcon("src/" + s_name[i] + ".jpg");
            	snackFoods[i] = new Food(imgIcon, s_name[i], Integer.parseInt(s_price[i]));
            	snackFoods[i].addActionListener(this);
            }
            snackPanel.add(snackFoods[i]);
            snackFoods[i].repaint();
        }      
        String add_n = "계란후라이,공기밥,계란,치즈";
	    String add_p = "500,1000,300,500";
		String[] a_name = add_n.split(",");
        String[] a_price = add_p.split(",");
        for (int i = 0; i < addFoods.length; ++i) {
        	if (daoList.list(a_name[i]) == 0) {
            	ImageIcon imgIcon = new ImageIcon("src/품절.jpg");
            	addFoods[i] = new Food(imgIcon, a_name[i], Integer.parseInt(a_price[i]));
            	addFoods[i].setEnabled(false);
            }else {
            	ImageIcon imgIcon = new ImageIcon("src/" + a_name[i] + ".jpg");
            	addFoods[i] = new Food(imgIcon, a_name[i], Integer.parseInt(a_price[i]));
                addFoods[i].addActionListener(this);
            }
            addPanel.add(addFoods[i]);
            addFoods[i].repaint();
        }  
        
        //주문내역서와 주문, 취소 버튼
        east_jp.setLayout(new BorderLayout());
        mini_jp.setLayout(new GridLayout(1, 4));
        setTextArea();
        east_jp.setPreferredSize(new Dimension(300, 300));
        east_jp.add("Center", menu_ta);
        east_jp.add("South", mini_jp);
        mini_jp.add(money_bt);
        money_bt.addActionListener(this);
        mini_jp.add(card_bt);
        card_bt.addActionListener(this);
        mini_jp.add(cancle_bt);
        cancle_bt.addActionListener(this);
        mini_jp.add(exit_bt);
        exit_bt.addActionListener(this);
        
        //창 닫기
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    //주문내역 넣기
    public void setTextArea() {
        menu_ta.setText("\t           주문내역서");
        menu_ta.append("\n===========================================");
        total = 0;
        for (Food fd : list) {
            menu_ta.append("\n" + fd.getName() + " \t\t" + df.format(fd.getPrice()) + "원");
            total += fd.getPrice();
        }
        if (total > 0) {
            menu_ta.append("\n===========================================");
            menu_ta.append("\n총금액 \t\t" + df.format(total) + "원");
        }
    }
    
    //주문, 취소시 담은 메뉴 정리
    public void clear() {
        menu_ta.setText("\t           주문내역서");
        menu_ta.append("\n===========================================");
        list.clear();
        total = 0;
    }
    
    public FoodGUI(String title) {
        super(title);
        this.init();
        super.setSize(1300, 1000);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int xpos = (int) (screen.getWidth() / 2 - this.getWidth() / 2);
        int ypos = (int) (screen.getHeight() / 2 - this.getHeight() / 2);
        
        this.setLocation(xpos, ypos);
        this.setResizable(false);
        this.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
    	//버튼 클릭시 분류된 메뉴 이미지 패널로 전환
    	if (e.getSource() == best_bt) {
    		cards.show(center_jp, "best");
    	}else if (e.getSource() == noodle_bt) {
    		cards.show(center_jp, "noodle");
    	}else if (e.getSource() == rice_bt) {
    		cards.show(center_jp, "rice");
    	}else if (e.getSource() == snack_bt) {
    		cards.show(center_jp, "snack");
    	}else if (e.getSource() == coffee_bt) {
    		cards.show(center_jp, "coffee");
    	}else if (e.getSource() == add_bt) {
    		cards.show(center_jp, "add");
    	}else if (e.getSource() == side_bt) {
    		cards.show(center_jp, "side");
    	}
    	
    	//현금이나 카드 결제
    	if (e.getSource() == money_bt || e.getSource() == card_bt) { 
    		for (Food food : list) {
    			String menuName = food.getName(); 
    			menu_n += " + " + menuName;
    			if (daoList.list(menuName) > 0) {
    				if (dao.update(menuName)) {
    				}else {
    					JOptionPane.showMessageDialog(this, menuName + "이/가 주문 실패했습니다." );
    			}
    			
    			//재고 부족일 경우
    			}else {
    				JOptionPane.showMessageDialog(this, menuName + "은/는 품절입니다.");
    			}
    		}
    		exit.updateMoney(total, str);
    		JOptionPane.showMessageDialog(this, "주문이 완료됐습니다.");
    		
    		
    		list.clear();
    		menu_n = "";
    		clear();
    	}
    	
    	//취소 버튼
    	else if (e.getSource() == cancle_bt) {
    		list.clear();
    		clear();
    	}
    	
    	//나가기 버튼
    	else if (e.getSource() == exit_bt) {
    		list.clear();
    		dispose();
    	}
    	
    	//버튼 클릭시 주문서에 이름, 가격 넣기
    	for (Food[] foods : new Food[][]{bestFoods, noodleFoods, riceFoods, snackFoods, sideFoods, addFoods, coffeeFoods}) {    	
    		for (int i = 0; i < foods.length; i++) {
                if (foods[i] == e.getSource()) {
                    list.add(foods[i]);
                    setTextArea();
                    break;
                }
    		}
		}
}
}
