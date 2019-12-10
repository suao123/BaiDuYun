package com.example.baiduyun.utils;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class FileIO {

    private static final String fileName = "cookie.txt";
    private Context mContext;

    public FileIO(){}

    public FileIO(Context mContext){
        super();
        this.mContext = mContext;
    }


    public void saveCookie(String fileContent) throws Exception {
        FileOutputStream output = mContext.openFileOutput(fileName, Context.MODE_PRIVATE);
        output.write(fileContent.getBytes());
        output.close();
    }

    public String readCookie() throws Exception{
        FileInputStream input = mContext.openFileInput(fileName);
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
