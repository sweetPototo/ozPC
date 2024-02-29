package view;

class UserDTO2 {
	
	 int no;
     String id;
     String pwd;
     String pwd1;
     String name;
     String birth;
     String phone;
     String email;

    public UserDTO2(String id, String pwd, String pwd1, String name, String birth, String phone, String email) {
        setId(id);
        setPwd(pwd);
        setPwd1(pwd1);
        setName(name);
        setBirth(birth);
        setPhone(phone);
        setEmail(email);
    }
   public UserDTO2(/*String id, String pwd, String name, String birth, String phone, String email*/) {
	  /* setId(id);
       setPwd(pwd);
       setName(name);
       setBirth(birth);
       setPhone(phone);
       setEmail(email);
  */ }
	
	public UserDTO2(String id) {
        setId(id);
    }
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
 }