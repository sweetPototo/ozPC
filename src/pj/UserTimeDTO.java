package pj;

public class UserTimeDTO {   // 데이터 전달 : 가변 객체
   private int no;
   private String id;
   private String pwd;
   private String pwd1;
   private String name;
   private String birth;
   private String phone;
   private String email;
   private int time;
   
   public int getNo() {
      return no;
   }
   public void setNo(int no) {
      this.no = no;
   }
   public String getId() {
      return id;
   }
   public void setId(String id) {
      this.id = id;
   }
   public String getPwd() {
      return pwd;
   }
   public void setPwd(String pwd) {
      this.pwd = pwd;
   }
   public String getPwd1() {
      return pwd1;
   }
   public void setPwd1(String pwd1) {
      this.pwd1 = pwd1;
   }
   public String getName() {
      return name;
   }
   public void setName(String name) {
      this.name = name;
   }
   public String getBirth() {
      return birth;
   }
   public void setBirth(String birth) {
      this.birth = birth;
   }
   public String getPhone() {
      return phone;
   }
   public void setPhone(String phone) {
      this.phone = phone;
   }
   public String getEmail() {
      return email;
   }
   public void setEmail(String email) {
      this.email = email;
   }
   public int getTime() {
      return time;
   }
   public void setTime(int time) {
      this.time = time;
   }
   
   


}