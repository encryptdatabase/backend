package dto;

public class UserInfo {
    private String user_name;  
    private String user_age;  
    private String user_hobby;  
    private String mtext;  
    private String id;
    public String get_id() {  
        return this.id;  
    }
    public void set_id(String id) {  
        this.id = id;  
    } 
    public String getUser_name() {  
        return user_name;  
    }  
    public void setUser_name(String user_name) {  
        this.user_name = user_name;  
    }  
    public String getUser_age() {  
        return user_age;  
    }
    public void setUser_age(String user_age) {  
        this.user_age = user_age;  
    }  
    public String getUser_hobby() {  
        return user_hobby;  
    }  
    public void setUser_hobby(String user_hobby) {  
        this.user_hobby = user_hobby;  
    }   
    public String getMtext() {  
        return mtext;  
    }  
    public void setMtext(String mtext) {  
        this.mtext = mtext;  
    }  
}
