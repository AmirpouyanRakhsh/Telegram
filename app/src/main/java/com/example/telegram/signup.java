package com.example.telegram;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ref.WeakReference;
import java.net.Socket;

public class signup extends AppCompatActivity {
    Socket socket;
    EditText fullname;
    EditText username;
    EditText password;
    Button signupbtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


    }


}
class signup_check extends AsyncTask<String,Void,String>{


    Socket socket;
    ObjectOutputStream out;
    ObjectInputStream in;
    boolean result;
    WeakReference<signup> activityRefrence;
    User user;

    signup_check(signup context){
        activityRefrence = new WeakReference<>(context);
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            socket = new Socket();
            out = new ObjectOutputStream(socket.getOutputStream());
            in= new ObjectInputStream(socket.getInputStream());
            out.writeObject(strings);
            out.flush();
            result=in.readBoolean();

            if (result){
                user = (User) in.readObject();
                System.out.println(user.username);
            }
            out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        signup activity = activityRefrence.get();
        if (activity == null || activity.isFinishing()){
            return;
        }

        if (activityRefrence.get().username.getText().toString().trim().length() == 0 || activityRefrence.get().password.getText().toString().trim().length() == 0) {
            Toast.makeText(activity , "all fields should be filled" , Toast.LENGTH_LONG).show();
        }
        else if (activityRefrence.get().password.getText().toString().trim().length() < 5){
            Toast.makeText(activity , "password is too short!" , Toast.LENGTH_LONG).show();
        }
        else if (result){
            Toast.makeText(activity, "You're Logged in Successfully", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(activity.getApplicationContext(), main_page.class);
            intent.putExtra("user" , user);
            activity.startActivity(intent);
        }else{
            Toast.makeText(activity, "Wrong Username Or Password !", Toast.LENGTH_LONG).show();
        }

    }
}

