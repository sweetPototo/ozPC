package pj;


import java.io.Serializable;

public class ClientMessage implements Serializable{
    private String sendLoginox;
    private String sendId;
    private String sendMenuList;
    private String sendChat;
    private String sendType;
    private int sendAddTime;
    
    public void disp() {
    	System.out.println("[cm ³»¿ë]");
    	System.out.println("sendId : " + sendId);
        System.out.println("sendLoginox : " + sendLoginox);
        System.out.println("sendType : " + sendType);
        System.out.println("sendMenuList : " + sendMenuList);
        System.out.println("sendAddTime : " + sendAddTime);
        System.out.println("sendChat : " + sendChat);
    }

    public String getSendLoginox() {
        return sendLoginox;
    }
    public void setSendLoginox(String sendLoginox) {
        this.sendLoginox = sendLoginox;
    }
    public String getSendId() {
        return sendId;
    }
    public void setSendId(String sendId) {
        this.sendId = sendId;
    }
    public String getSendMenuList() {
        return sendMenuList;
    }
    public void setSendMenuList(String sendMenuList) {
        this.sendMenuList = sendMenuList;
    }
    public int getSendAddTime() {
        return sendAddTime;
    }
    public void setSendAddTime(int sendAddTime) {
        this.sendAddTime = sendAddTime;
    }

    public String getSendChat() {
        return sendChat;
    }

    public void setSendChat(String sendChat) {
        this.sendChat = sendChat;
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

}