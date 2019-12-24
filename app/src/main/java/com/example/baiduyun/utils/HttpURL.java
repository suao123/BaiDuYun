package com.example.baiduyun.utils;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HttpURL {

    private static final String URL = "http://azks.womoe.top/api/";
    private static final String FILEURL = "http://azks.womoe.top/files";

    public JSONObject getURLResource(String apiType, String requestMode, HashMap<String, String> requestResource, HashMap<String, String> cookie) throws Exception {
        int code = 0;
        java.net.URL url;
        JSONObject content;
        HttpURLConnection connect;
        StringBuilder str_content = new StringBuilder();
        if(requestMode.equals("POST")){
            StringBuilder web_URL = new StringBuilder(URL + apiType);
            url = new URL(web_URL.toString());
            JSONObject json = new JSONObject(requestResource);
            connect = (HttpURLConnection) url.openConnection();
            connect.setRequestMethod(requestMode);
            connect.setRequestProperty("Content-Type", "application/json");
            connect.setRequestProperty("cookie", "token=" + cookie.get("token"));
            connect.setReadTimeout(8000);
            OutputStream out = new DataOutputStream(connect.getOutputStream());
            out.write(json.toString().getBytes());
            System.out.println(json.toString());
            out.flush();
            out.close();

        } else {
            List<String> requestKeys = new ArrayList<>(requestResource.keySet());
            StringBuilder web_URL = new StringBuilder(URL + apiType + "?");
            for (String s: requestKeys) {
                web_URL.append(s).append("=").append(requestResource.get(s)).append("&");
            }
            web_URL.deleteCharAt(web_URL.length() - 1);
            url = new URL(web_URL.toString());
            connect = (HttpURLConnection) url.openConnection();
            connect.setRequestMethod(requestMode);
            connect.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connect.setReadTimeout(8000);
            if(cookie != null){
                connect.setRequestProperty("cookie", "token=" + cookie.get("token"));
            }
        }


        code = connect.getResponseCode();
        System.out.println(code);
        //if(code == HttpURLConnection.HTTP_OK){
            //将响应流转换成字符串
            BufferedReader reader = new BufferedReader(new InputStreamReader(connect.getInputStream()));
            String line = "";
            while ((line = reader.readLine())!=null){
                str_content.append(line);
            //}
        }
        content = new JSONObject(String.valueOf(str_content));
        return content;
    }

    public InputStream getFile(String username, String filename) throws Exception{
        int code = 0;
        java.net.URL url;
        JSONObject content;
        HttpURLConnection connect;
        StringBuilder web_URL = new StringBuilder(FILEURL);
        web_URL.append("/").append(username)
               .append("/").append(filename);
        url = new URL(web_URL.toString());
        connect = (HttpURLConnection) url.openConnection();
        connect.setRequestMethod("GET");
        connect.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connect.setReadTimeout(8000);
        code = connect.getResponseCode();
        System.out.println(code);
        if(code == HttpURLConnection.HTTP_OK){
            return  connect.getInputStream();
        }
        return null;
    }

}
