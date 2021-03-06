package com.example.baiduyun.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;

public class FileIO {

    private static final String fileCookie = "cookie.txt";
    private static final String fileUsername = "username.txt";
    @SuppressLint("SdCardPath")
    private static final String userFile = "/mnt/sdcard/BAIDUYUN";
    private Context mContext;

    public FileIO(Runnable runnable){}

    public FileIO(Context mContext){
        super();
        this.mContext = mContext;
        File file = new File(userFile);
        if (!file.exists()){
            file.mkdir();
        }
    }


    public void saveCookie(String fileContent) throws Exception {
        FileOutputStream output = mContext.openFileOutput(fileCookie, Context.MODE_PRIVATE);
        output.write(fileContent.getBytes());
        output.close();
    }

    public String readCookie() throws Exception{
        FileInputStream input = mContext.openFileInput(fileCookie);
        byte[] temp = new byte[1024];
        StringBuilder stringBuilder = new StringBuilder("");
        int len = 0;
        while ((len = input.read(temp)) > 0){
            stringBuilder.append(new String(temp, 0, len));
        }
        input.close();
        return stringBuilder.toString();
    }

    public void saveUsername(String username) throws Exception {
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public HashMap<String, String> uploadFile(String filePath) throws Exception {
        HashMap<String, String> result = new HashMap<>();
        File file = new File(filePath);
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] data = new byte[fileInputStream.available()];
        fileInputStream.read(data);
        String content = Base64.getEncoder().encodeToString(data);
        result.put("extend",file.getName().substring(file.getName().indexOf(".")));
        result.put("filename", file.getName());
        result.put("base64File", content);
        return result;
    }

    public void downloadFile(InputStream input, String filename) throws Exception {
        System.out.println(input);
        File file = new File(userFile, filename);
        file.createNewFile();

        FileOutputStream outputStream = new FileOutputStream(file);
        byte[] buf = new byte[512];
        int len = 0;
        while((len = input.read(buf)) != -1)
            outputStream.write(buf, 0, len);
    }
}
