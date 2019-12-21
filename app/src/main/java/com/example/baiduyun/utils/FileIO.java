package com.example.baiduyun.utils;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class FileIO {

    private static final String fileName = "cookie.txt";
    private static final String fileUsername = "username.txt";
    private Context mContext;

    public FileIO(){}

    public FileIO(Context mContext){
        super();
        this.mContext = mContext;
    }

    public void saveUsername(String username) throws Exception{
        FileOutputStream output = mContext.openFileOutput(fileUsername, Context.MODE_PRIVATE);
        output.write(username.getBytes());
        output.close();
    }

    public String readUsername() throws Exception{
        FileInputStream input = mContext.openFileInput(fileUsername);
        byte[] temp = new byte[1024];
        StringBuilder stringBuilder = new StringBuilder("");
        int len = 0;
        while ((len = input.read(temp)) > 0){
            stringBuilder.append(new String(temp, 0, len));
        }
        input.close();
        return stringBuilder.toString();
    }

    public void saveCookie(String fileContent) throws Exception {
        FileOutputStream output = mContext.openFileOutput(fileName, Context.MODE_PRIVATE);
        output.write(fileContent.getBytes());
        output.close();
    }

    public String readCookie() throws Exception{
        File file = new File(mContext.getFilesDir()+ "/" +fileName);
        FileInputStream input = new FileInputStream(file);
        byte[] temp = new byte[1024];
        StringBuilder stringBuilder = new StringBuilder("");
        int len = 0;
        while ((len = input.read(temp)) > 0){
            stringBuilder.append(new String(temp, 0, len));
        }
        input.close();
        return stringBuilder.toString();
    }
}
