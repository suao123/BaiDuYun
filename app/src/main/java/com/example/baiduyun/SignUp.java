package com.example.baiduyun;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.baiduyun.utils.HttpURL;
import com.example.baiduyun.utils.Toasttip;

import org.json.JSONObject;

import java.util.HashMap;

public class SignUp extends AppCompatActivity {

    private EditText txt_phone;
    private EditText txt_password;
    private EditText txt_conf_password;
    private Button btn_signup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        txt_phone = findViewById(R.id.signUp_id);
        txt_password = findViewById(R.id.signUp_password);
        txt_conf_password = findViewById(R.id.signUp_conf_password);
        btn_signup = findViewById(R.id.button_signUp);


        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Toasttip tip = new Toasttip(getApplicationContext());
                        if (txt_phone.getText().equals("")){
                            tip.showTip("账号为空");
                        } else if(txt_password.getText().equals("")){
                            tip.showTip("密码为空");
                        } else if(txt_phone.getText().length() != 11){
                            tip.showTip("账号长度不为11");
                        }

                        JSONObject result;
                        HttpURL httpURL = new HttpURL();

                        boolean temp = txt_password.getText().toString().equals(txt_conf_password.getText().toString());

                        if(temp){
                            HashMap<String, String> requestResource = new HashMap<>();
                            requestResource.put("username", txt_phone.getText().toString());
                            requestResource.put("password", txt_password.getText().toString());
                            try {
                                result = httpURL.getURLResource("api_reg", "GET", requestResource);
                                if(result.get("status").equals("success")){
                                    if (Looper.myLooper() != Looper.getMainLooper()) {
                                        Handler mainThread = new Handler(Looper.getMainLooper());
                                        mainThread.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                        });
                                        return;
                                    }
                                } else if(result.get("status").equals("exists")){
                                    tip.showTip("您的账号已被注册");
                                } else if(result.get("status").equals("failed")){
                                    tip.showTip("注册失败");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            tip.showTip("您输入的密码不一致");
                        }
                    }
                }).start();
            }
        });
    }


}
