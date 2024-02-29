package pj;
import java.io.*;
import java.util.*;

import view.*;

import java.net.*;
import client.*;
import pj.*;

// 오즈 서버 실행
public class OzServer{
	private ServerSocket ss;
	private Socket soc;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private Scanner sc;
	private ClientMessage cm;
	
	// 객체를 구분하기 위해 hashtable 자료 구조 사용
	// hashtable 자료 구조 선택 이유 :
	// hashtable은 동기화된 메서드로 구성되어 있기 때문에 멀티 스레드가 동시에 hashtable의 메소드들을
	// 실행할 수 없고, 따라서 멀티 스레드 상황에서도 안전하게 객체를 추가하고 삭제할 수 있다
	
	private Hashtable<String, ClientMessage> clientMHT  = new Hashtable<>();	// 클라이언트를 구분하는 hashtable
	private Hashtable<String, Integer> seatNum = new Hashtable<>();	// 자리를 구분하는 hashtable
	private Hashtable<String, TimeSetThread> timeSetHt = new Hashtable<>();	
	private Hashtable<String, ChatServerGUI>chatHt = new Hashtable<>();	// 채팅 프로그램 켜져 있는지 꺼져 있는지 확인
	private Hashtable<String, ServerChatThread>serChatHt = new Hashtable<>();
	
	UserTimePro userTime = new UserTimeProImpl();
	OzServerGui oz = new OzServerGui("관리자 화면");
	
	// 오즈 서버 구동
	public OzServer() {
		try {
			ss = new ServerSocket(24511);
			System.out.println("서버 대기 중.....");
			
			//sc = new Scanner(System.in);
			
			//serverGUI = new view.OzServerGui("관리자 서버");
			
			while (true) {
				soc = ss.accept();	
				System.out.println(soc.toString());// 소켓이 서버소켓을 받을 때마다
				OzClient ozc = new OzClient(soc);	// 오즈 클라이언트 객체 스레드 실행
				ozc.start();						// 클라이언트 스레드 스타트
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	// 로그인 하면 => DB에 있는 시간 확인 후 클라이언트에게 시간 알려주기
    public void login(Socket soc, String id) {
    	cm = clientMHT.get(id);	
        System.out.println("server sendTime id : " + id);
        oz.addMessageToAlarm("@" + id + "님이 로그인 하였습니다.\n");
        
        // 자리 랜덤 배정 : 이미 선택된 자리는 배정 X
        boolean duplication;
        while (true) {
            Enumeration<String> keys = seatNum.keys();
            int seat = (int) ((Math.random() * 30) );
            duplication = false; // 루프 시작 시 duplication을 초기화

            while (keys.hasMoreElements()) {
                String key = keys.nextElement(); // 현재 반복 중인 키 가져오기
                if (seatNum.get(key).equals(seat)) {
                    System.out.println("자리 " + seat + "에 이미 다른 사용자가 배정되어 있습니다.");
                    duplication = true;
                    break;
                }
            }
            if (duplication) continue;
            seatNum.put(id, seat);
            System.out.println("id :" + seatNum.get(id));
            break;
        }
		System.out.println("자리 " + (seatNum.get(id)+1) + "번 에 " + id + " 배정 완료.");
    }
    
	
    // 로그아웃 하면 => 아이디에 남은 시간 값 DB에 저장하기
    public void logout(Socket soc, String id) throws Exception{
    	try {
    		// 서버가 로그아웃 한다고 받은 id                  
            System.out.println("server get logout id : " + cm.getSendId());
            // 서버에 메시지 보내기
            oz.addMessageToAlarm("@" + cm.getSendId() + "님이 로그아웃 하였습니다.\n");
            int num = seatNum.get(id);
            System.out.println("자리 값 : " + num);
            oz.ititial_seat(num+1);
            seatNum.remove(id);
            
        } catch (Exception e) {
            e.printStackTrace();
        }	
    }
  
	// 클라이언트 객체 스레드
	class OzClient extends Thread {
		private boolean stop = false;
		Socket soc;
		ClientMessage cm;
		private ObjectOutputStream oos;
		private ObjectInputStream ois;

		OzClient (Socket soc) {	// 클라이언트가 보낸 신호 읽기
			this.soc = soc;
		}

		public void run() {
			while (true) {
				try{
					try {
		        	oos = new ObjectOutputStream(new BufferedOutputStream((soc.getOutputStream())));
		        	ois = new ObjectInputStream(new BufferedInputStream((soc.getInputStream())));
		            cm = (ClientMessage) ois.readObject();
		            cm.disp();
					}catch(Exception e) {}
					
					String type = cm.getSendType();
					String id = cm.getSendId();
					System.out.println(id);
					System.out.println(type);
					if ("login".equals(type)) {
						clientMHT.put(id, cm);	// id 값이랑 clientMessage 객체 저장
						int time = userTime.UserLogin(id);		// DB에서 id 값에 저장된 time 불러오기
						//System.out.println(time);
						login(soc, id);		// 로그인 메서드 시작
						int addtime = 0;
						TimeSetThread tst = new TimeSetThread(soc, id, time, addtime); // time thread 시작
						timeSetHt.put(cm.getSendId(), tst);	// timethread 객체 저장
						tst.start();	// 스레드 시작
						//oos.close();
					}else if("logout".equals(type)){
						try {
							logout(soc, id);	// 여기를 못 넘어가고 뺑뺑도는 거임
							System.out.println("로그아웃 된 거임");
							//로그아웃 하는 timeSetThread 가져오기
							System.out.println(timeSetHt.get(cm.getSendId()));
							TimeSetThread tst_logout = timeSetHt.get(cm.getSendId());
							tst_logout.interrupt();	// interrupt 발생시켜 스레드 중지
							ClientMessage oldcm = clientMHT.get(cm.getSendId());

							try {
								chatHt.remove(id);	// 채팅 로그인 기록 삭제
								ServerChatThread sct = serChatHt.get(id);	// 채팅 스레드 중지
								sct.interrupt();
								serChatHt.remove(cm.getSendId());		// 채팅 스레드 중지
								
							}catch(Exception e) {
								System.out.println("채팅을 실행하지 않은 손님");
							}
							
							// 클라이언트 객체 가져오기
							userTime.UserLogout(id, timeSetHt.get(cm.getSendId()).getTime());
							System.out.println("DB에 남은 시간 저장 : " + timeSetHt.get(cm.getSendId()).getTime());
							clientMHT.remove(cm.getSendId(), oldcm);	// 객체 삭제
							//timeSetHt.remove(cm.getSendId(), tst_logout);	// 스레드 삭제
							
							//oos.close();
							this.interrupt();
							break;
						}catch(EOFException e){
							e.printStackTrace();
						}
					}else if("Time".equals(type)) {
						TimeSetThread tst_logout = timeSetHt.get(cm.getSendId());
						// 기존 id 에 저장되어 있는 time set thread 가져옴
						int time = tst_logout.getTime();	// 기존 객체에 있는 시간 가져옴
						tst_logout.interrupt();	// interrupt 발생시켜 기존 스레드 중지
						int addtime = cm.getSendAddTime(); // 충전된 시간
						TimeSetThread tst = new TimeSetThread(soc, id, time, addtime);
						// 새로운 time thread 시작
						timeSetHt.put(cm.getSendId(), tst);
						tst.start();
						oz.addMessageToAlarm(seatNum.get(id) +" 번 자리 "+ cm.getSendId() + "님이 " + (addtime/60) + " 분 추가하셨습니다\n");
						//oos.close();
					}else if("Food".equals(type)) {
						oz.addMessageToAlarm("@" + cm.getSendId() + "님이 " + seatNum.get(id) + "번 자리에서" + cm.getSendMenuList() + " 주문하셨습니다\n");
						
					//oos.close();
					}else if("chat".equals(type)) {
						ServerChatThread sct = new ServerChatThread(soc, id, cm);
						serChatHt.put(id, sct);
						sct.start();
						// 만약 클라이언트에서 채팅을 나간다면?
						// 채팅 스레드가 닫혀야 하므로 클라이언트에서 chatStop 신호도 받기
					}
	
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		
		}	
	}
	
	// 시간 측정 스레드
	public class TimeSetThread extends Thread {
		Socket soc;
		String id;
		int time;
		int addtime;
		int sum;
		
		public TimeSetThread(Socket soc, String id, int time, int addtime) {
			this.soc = soc;
			this.id = id;
			this.time = time;
			this.addtime = addtime;
			sum = time+addtime;
		}
		
		public int getTime() {
			return sum;
		}
		
		public void run() {
            while (!interrupted()) {

            	// System.out.println("보낸 시간" + sum);
                oz.set_start_seat((seatNum.get(cm.getSendId())+1), sum);	// 시간을 분으로 전송
                sendTime(id, sum);
                
                
                try {
                    Thread.sleep(60000);	// 60초동안 대기
                }catch (Exception e) {
                    //e.printStackTrace();
                    break;
                }
                
                sum -= 60; // 시간을 60초씩 감소
                
            }
            System.out.println("리소스 정리");
            System.out.println("타임 스레드 종료");
		}
	}

	// 클라이언트에게 시간 보내는 스레드
	public void sendTime(String id, int time) {
    	try {
    		oos = new ObjectOutputStream(new BufferedOutputStream((soc.getOutputStream())));
			ClientMessage cm = new ClientMessage();
			cm.setSendAddTime(time);
			cm.setSendType("Time");
			cm.disp();
			
			try {
				oos.writeObject(cm);
				oos.flush();
			}catch(IOException e) {
				e.printStackTrace();
			}
			System.out.println("클라이언트로 잔여시간 전달 성공!" + time);

		}catch(Exception e) {
			e.printStackTrace();
		}
    }
	
	// 메시지 받는 스레드 : 메시지 전송은 ChatServerGUI 에서 함
	public class ServerChatThread extends Thread implements Serializable {
		Socket soc;
		String id;
		boolean duplication;
		ClientMessage cm;
		
		public ServerChatThread(Socket soc, String id, ClientMessage cm) {
			this.soc = soc;
			this.id = id;
			this.cm = cm;

			// 만약 채팅이 켜져 있지 않다면? 
	        while (true) {
	        	
	            Enumeration<String> keys = chatHt.keys();
	            duplication = false; // 루프 시작 시 duplication을 초기화

	            while (keys.hasMoreElements()) {
	            	try {
	            		if (!chatHt.get(id).isVisible()) break;
	            	}catch(Exception e) {}
	                String key = keys.nextElement(); // 현재 반복 중인 키 가져오기
	                if (key.equals(id)) {
	                    System.out.println(id + " 님과의 채팅이 켜져 있습니다.");
	                    duplication = true;
	                    break;
	                }
	            }
	            if (duplication) break;
	            oz.addMessageToAlarm("@" + cm.getSendId() + "님이 채팅을 시작하셨습니다.\n");
	            ChatServerGUI csg = new ChatServerGUI(id + "님 과의 채팅입니다", soc, id);
	            chatHt.put(id, csg);
	            System.out.println(id + " 님과의 채팅이 켜졌습니다.");
	            break;
	        }
			
		}
		
		public void run() {
			try{
				String idcm = cm.getSendId();
				String message = cm.getSendChat();
				
				
				chatHt.get(id).clientMessage(id, message);
				// 클라이언트 메시지를 chatServerGUI에게 보내주기-
				System.out.println(cm.getSendId());
				System.out.println(cm.getSendChat());
				// csg에 id값과 message 값을 줘서 클라이언트 메시지 받기
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	

    
	
	public static void main(String[] args) {
		new OzServer();
	}
}