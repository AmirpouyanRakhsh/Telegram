
package com.example.telegram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button loginbtn;
    Button signupbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_login);

        loginbtn = findViewById(R.id.loginbutton);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        signupbtn = findViewById(R.id.signupbutton);
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

    }
    public void signup(){
        Intent intent=new Intent(MainActivity.this,signup.class);
        startActivity(intent);
    }

    public void login(){
        Intent intent=new Intent(MainActivity.this,login.class);
        startActivity(intent);
    }
}
