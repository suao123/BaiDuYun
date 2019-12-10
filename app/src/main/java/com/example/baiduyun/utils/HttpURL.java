package com.example.baiduyun.utils;

import android.os.Looper;
import android.util.ArraySet;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class HttpURL {

    private static final String URL = "http://file.womoe.top/api/";

    public JSONObject getURLResource(String apiType, String requestMode,HashMap<String, String> requestResource) throws Exception {
        int code = 0;
        java.net.URL url;
        JSONObject content;
        HttpURLConnection connect;
        StringBuilder str_content = new StringBuilder();
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

        code = connect.getResponseCode();
        System.out.println(code);
        if(code == HttpURLConnection.HTTP_OK){
            //将响应流转换成字符串
            BufferedReader reader = new BufferedReader(new InputStreamReader(connect.getInputStream()));
            String line = "";
            while ((line = reader.readLine())!=null){
                str_content.append(line);
            }
        }

        content = new JSONObject(String.valueOf(str_content));
        return content;
    }
}
