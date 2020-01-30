package com.example.telegram;

import java.io.Serializable;

public class User implements Serializable {

    private String username, password;

    public User(String username, String pass) {
        this.username = username;
        this.password = pass;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

}
