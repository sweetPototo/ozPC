package pj;
import java.io.*;
import java.util.*;
import java.net.*;
import java.sql.*;

public class Server extends Thread{
	UserTimePro userTime = new UserTimeProImpl();
	private ServerSocket ss;
	private Socket soc;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private Scanner in;
	
	String id;
    public Server() {
        	try {
    			ss = new ServerSocket(24511);
    			System.out.println("서버 대기중......");
    			soc = ss.accept();
    			oos = new ObjectOutputStream(soc.getOutputStream());
    			ois = new ObjectInputStream(soc.getInputStream());
    			System.out.println("클라이언트와 연결 완료!");
    			this.start();
    			sendTime(id);
    		}catch(IOException e) {
    			e.printStackTrace();
    		}
    }
    //받기
    public void run() {
    	try {
    	Object obj = ois.readObject();
    	ClientMessage cm = (ClientMessage)obj;
    	System.out.println("[클라이언트가 보낸 cm]" + "\n");
    	cm.disp();
    	}catch(IOException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			
		}
    }
    
    //보내기
    public void sendTime(String id) {
    	try {
    		int time=95003;
			while(true) {
				ClientMessage cm = new ClientMessage();
				cm.setSendAddTime(time);
				cm.setSendType("Time");
				try {
					oos.writeObject(cm);
				}catch(IOException e) {
					e.printStackTrace();}
				System.out.println("클라이언트로 잔여시간 전달 성공!" + time);
				time += 60;
				Thread.sleep(1000);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
    }
    public static void main(String[] args) {
    	new Server();
    	 
    }
    
}