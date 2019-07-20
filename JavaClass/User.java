package com.example.bottomtabber;

import org.litepal.crud.LitePalSupport;

public class User extends LitePalSupport {

    private String username;
    private String password;
    public User(String username,String password){
        this.username=username;
        this.password=password;
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

    @Override
    public boolean equals( Object obj) {
        if(obj instanceof User){
            User u= (User) obj;
            return this.username.equals(u.username) && this.password.equals(u.password);
        }
        return super.equals(obj);
    }
}
