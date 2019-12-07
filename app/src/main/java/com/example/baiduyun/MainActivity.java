package com.example.baiduyun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.baiduyun.constant.Constant;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    private Button btn_singUp;
    private Button btn_login;
    private EditText txt_id;
    private EditText txt_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_singUp = findViewById(R.id.button_singup);
        btn_login = findViewById(R.id.button_login);
        txt_id = findViewById(R.id.login_id);
        txt_password = findViewById(R.id.login_password);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        StringBuffer str_content = new StringBuffer();
                        String login_web = Constant.URL + "api_login?username=" + txt_id.getText() + "&password=" + txt_password.getText();
                        try {
                            URL url = new URL(login_web);
                            HttpURLConnection connect = (HttpURLConnection) url.openConnection();
                            connect.setRequestMethod("GET");
                            connect.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                            connect.setReadTimeout(8000);
                            connect.setReadTimeout(8000);

                            int code = connect.getResponseCode();
                            if (code == 200){
                                //将响应流转换成字符串
                                BufferedReader reader = new BufferedReader(new InputStreamReader(connect.getInputStream()));
                                String line = "";
                                while ((line = reader.readLine())!=null){
                                    str_content.append(line);
                                }
                                JSONObject content = new JSONObject(String.valueOf(str_content));

                                if(content.getString("status").equals("failed")){
                                    Looper.prepare();
                                    Toast.makeText(getApplicationContext(), "您输入的账号或者密码有误", Toast.LENGTH_LONG).show();
                                    Looper.loop();
                                } else if(content.get("status").equals("success")){
                                    Looper.prepare();
                                    Toast.makeText(getApplicationContext(), "成功登陆", Toast.LENGTH_LONG).show();
                                    Looper.loop();
                                }

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        btn_singUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this,SignUp.class);
                startActivity(intent);
            }
        });
    }

}
