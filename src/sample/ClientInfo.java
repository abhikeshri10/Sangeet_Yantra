package sample;

import javafx.scene.control.TextField;

import java.io.Serializable;

public class ClientInfo implements Serializable {
    public String name;
    public String email;
    public String phone;
    public String username;
    public String password;
    public boolean new_login;
    public int user_id;
    public ClientInfo(String name, String email, String phone, String username, String password)
    {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.username = username;
        this.password = password;

    }
    public ClientInfo(String name,String email,String phone,String username,boolean new_login,int user_id)
    {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.username = username;
        this.new_login = new_login;
        this.user_id = user_id;
    }
}
