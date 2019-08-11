package com.example.bottomtabber.Data;


import android.graphics.Bitmap;
import android.widget.ImageView;

import com.example.bottomtabber.R;

import org.litepal.crud.LitePalSupport;


public class User extends LitePalSupport {

    private String username;
    private String password;
    private String sex;
    private String birth;
    private String city;
    private String flag;
    private String email;

    public User(String username,String password){
        this.username=username;
        this.password=password;
        this.sex="sex";
        this.birth="birth";
        this.city="city";
        this.flag="flag";
        this.email="email";
    }
    public User(){ }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals( Object obj) {
        if(obj instanceof User){
            User u= (User) obj;
            return this.username.equals(u.username) && this.password.equals(u.password);
        }
        return super.equals(obj);
    }
}
