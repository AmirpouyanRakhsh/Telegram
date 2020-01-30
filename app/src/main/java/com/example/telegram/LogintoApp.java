package com.example.telegram;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ref.WeakReference;
import java.net.Socket;

public class LogintoApp extends AsyncTask<String,Void,String> {
    Socket socket;
    ObjectInputStream in;
    ObjectOutputStream out;
    int result;
    WeakReference<login> activityRefrence;
    byte[] photo;


    @Override
    protected String doInBackground(String... strings) {
        try {
            socket = new Socket();

            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            out.writeObject(strings);
            out.flush();

            result=in.readInt();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
