package com.example.telegram;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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
    @Override
    public boolean equals(@Nullable Object obj) {
        return this.username.equals(((User)obj).username);
    }

    static Set<User> users = new HashSet<>();

}
