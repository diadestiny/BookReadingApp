package com.example.bottomtabber.Data;


import android.graphics.Bitmap;
import android.widget.ImageView;

import com.example.bottomtabber.R;

import org.litepal.crud.LitePalSupport;


public class User extends LitePalSupport {

    private String username;
    private String password;
    private String headImage;
    private int sex; //0男1女
    public User(String username,String password){
        this.username=username;
        this.password=password;
        this.sex=0;
        this.headImage="no";
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
    public String getHeadImage() {
        return headImage;
    }
    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }
    public int getSex() {
        return sex;
    }
    public void setSex(int sex) {
        this.sex = sex;
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
