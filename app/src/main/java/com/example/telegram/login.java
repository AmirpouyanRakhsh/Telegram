package com.example.telegram;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class login extends AppCompatActivity {
    TextView textView;

    Activity activity;
    EditText username;
    EditText password;
    Button loginbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginbtn = findViewById(R.id.loginbutton);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotochatlist();
            }
        });

        textView = findViewById(R.id.backtosignuplogin);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returntopreviouspage(textView);
            }
        });
    }

    public void gotochatlist(){
        Intent intent = new Intent(login.this,chatlist.class);
        startActivity(intent);
    }

    public void returntopreviouspage(View view){
        Intent intent=new Intent(login.this,MainActivity.class);
        startActivity(intent);
    }

}
