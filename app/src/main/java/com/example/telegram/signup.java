package com.example.telegram;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ref.WeakReference;
import java.net.Socket;

public class signup extends AppCompatActivity implements View.OnClickListener{

    EditText fullname;
    EditText username;
    EditText password;
    EditText bio;


    CardView cardView;
    TextView textView;

    public static Object input;
    public static Object output;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        new MyTaskRegister().execute();

        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        fullname = (EditText)findViewById(R.id.fullname);
        bio = (EditText)findViewById(R.id.bio);

        textView = findViewById(R.id.backtosignuplogin);
        textView.setOnClickListener(this);

        cardView = (CardView) findViewById(R.id.signupbutton);
        cardView.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signupbutton:
                if (infoCheck())
                    gotochatlist();
                break;
            case R.id.backtosignuplogin:
                returntopreviouspage();
                break;
        }
    }

    public void gotochatlist(){
        Intent intent=new Intent(signup.this,chatlist.class);
        startActivity(intent);
    }

    public void returntopreviouspage(){
        Intent intent=new Intent(signup.this,MainActivity.class);
        startActivity(intent);
    }

    public Boolean infoCheck() {
        if (password.getText().toString().length() == 0 && username.getText().toString().length() == 0 && fullname.getText().toString().length() == 0) {
            password.setError("Password field can't be empty!");
            username.setError("Username field can't be empty!");
            fullname.setError("fullname field can't be empty!");
            return false;
        }
        if (username.getText().toString().length() == 0) {
            username.setError("Username field can't be empty!");
            return false;
        }

        if (password.getText().toString().length() == 0) {
            password.setError("Password field can't be empty!");
            return false;
        }

        if (fullname.getText().toString().length() == 0) {
            fullname.setError("fullname field can't be empty!");
            return false;
        }
        if (password.getText().toString().length() != 0 && password.getText().toString().length() < 6) {
            password.setError("Password must be longer than 5 characters");
            return false;
        } else {
            username.setError(null);
            password.setError(null);
            fullname.setError(null);
        }
        return true;
    }

}

class MyTaskRegister extends AsyncTask<String, Void, Void> {
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
        new Thread(new Runnable() { // for sending information
            @Override
            public void run() {
                while (true) {
                    if (signup.output != null) {
                        try {
                            output.writeObject(signup.output);
                            output.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        signup.output = null;
                    }
                }
            }
        }).start();
        new Thread(new Runnable() { // for receiving information
            @Override
            public void run() {
                while (true) {
                    try {
                        signup.input = input.readObject();
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



