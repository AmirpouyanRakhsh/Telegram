package com.example.telegram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class login extends AppCompatActivity implements View.OnClickListener {
    TextView textView;
    CardView cardView;


    EditText username;
    EditText password;

    public static Object input;
    public static Object output;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        new MyTaskLogin().execute();

        username =(EditText)findViewById(R.id.username);
        password =(EditText)findViewById(R.id.password);

        cardView=(CardView)findViewById(R.id.loginbutton);
        cardView.setOnClickListener(this);
        textView = findViewById(R.id.backtosignuplogin);
        textView.setOnClickListener(this);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backtosignuplogin:
                startActivity(new Intent(login.this, MainActivity.class));
                break;
            case R.id.loginbutton:
                infoCheck();
                loginCheck();
                break;
        }
    }

        public void infoCheck() {
            if (password.getText().toString().length() == 0 && username.getText().toString().length() == 0) {
                password.setError("Password field can't be empty!");
                username.setError("Username field can't be empty!");
            } else if (username.getText().toString().length() == 0)
                username.setError("Username field can't be empty!");
            else if (password.getText().toString().length() == 0)
                password.setError("Password field can't be empty!");
            else {
                username.setError(null);
                password.setError(null);
            }
        }

        public void loginCheck() {
            String user = username.getText().toString();
            String pass = password.getText().toString();
            User userLogin = new User(user, pass);
            output = userLogin;
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(input);

            try {
                if (input.equals("notValid"))
                    Toast.makeText(login.this, "Username is not valid!", Toast.LENGTH_LONG).show();
                if (input.equals("wrongPassword"))
                    Toast.makeText(login.this, "Password is wrong!", Toast.LENGTH_LONG).show();
                if (input.equals("loggedIn")) {
                    Toast.makeText(login.this, "You logged in successfully :)", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(login.this, chatlist.class));
                }
            } catch (NullPointerException e) {
                e.getMessage();
            }
        }



}
class MyTaskLogin extends AsyncTask<String, Void, Void> {
    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    @Override
    protected Void doInBackground(String... strings) {
        try {

            socket = new Socket("172.20.176.95", 6666);
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(new Runnable() { //for sending information
            @Override
            public void run() {
                while (true) {
                    if (login.output != null) {
                        try {
                            output.writeObject(login.output);
                            output.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        login.output = null;
                    }
                }
            }
        }).start();
        new Thread(new Runnable() { //for receiving information
            @Override
            public void run() {
                while (true) {
                    try {
                        login.input = input.readObject();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        return null;
    }

}