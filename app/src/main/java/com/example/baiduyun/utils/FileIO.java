package com.example.baiduyun.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Base64;

public class FileIO {

    private static final String fileName = "cookie.txt";
    @SuppressLint("SdCardPath")
    private static final String userFile = "/mnt/sdcard/";
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public JSONObject uploadFile(String filePath) throws Exception {
        JSONObject result = new JSONObject();
        File file = new File(filePath);
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] data = new byte[fileInputStream.available()];
        fileInputStream.read(data);
        String content = Base64.getEncoder().encodeToString(data);
        result.put("filename", file.getName());
        result.put("base64File", content);
        return result;
    }
}
