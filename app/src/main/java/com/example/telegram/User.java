package com.example.telegram;

import android.hardware.usb.UsbRequest;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    String username;
    String password;
    byte[] photo;
    ArrayList<User> contact=new ArrayList<>();
    public User(String username,String password){
        this.username=username;
        this.password=password;
    }
    public User(String username,String password,byte[] photo){
        this.username=username;
        this.password=password;
        this.photo=photo;
    }

}
