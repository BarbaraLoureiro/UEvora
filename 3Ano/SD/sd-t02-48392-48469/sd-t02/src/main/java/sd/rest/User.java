package sd.rest;

import javax.xml.bind.annotation.*;

/**
 *
 * @author barbara
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "user")
public class User {
    
    @XmlElement(required = false)
    protected String uid;
    
    @XmlElement(required = true)
    protected String username;
    
    @XmlElement(required = true)
    protected String email;
    
    @XmlElement(required = true)
    protected String password;
    
    @XmlElement(required = true)
    protected String usertype;
    
    public User(){
        this.username="";
        this.email="";
        this.password="";
        this.usertype= "";
    }
    
    public User(String username, String email, String password, String usertype){
        this.username=username;
        this.email=email;
        this.password=password;
        this.usertype=usertype;
    }
    
    public User(String uid, String username, String email, String password, String usertype){
        this.uid=uid;
        this.username=username;
        this.email=email;
        this.password=password;
        this.usertype=usertype;
    }
    
    public String getUid(){
        return uid;
    }
    
    public void setUid(String uid){
        this.uid=uid;
    }
    
    public String getUsername(){
        return username;
    }
    
    public void setUsername(String username){
        this.username=username;
    }
    
    public String getEmail(){
        return email;
    }
    
    public void setEmail(String email){
        this.email=email;
    }
    
    public String getPassword(){
        return password;
    }
    
    public void setPassword(String password){
        this.password=password;
    }
    
    public String getUsertype(){
        return usertype;
    }
    
    public void setUsertype(String usertype){
        this.usertype=usertype;
    } 
}
