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

import com.example.baiduyun.utils.FileIO;
import com.example.baiduyun.utils.HttpURL;
import com.example.baiduyun.utils.Toasttip;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


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
                        JSONObject result = null;
                        HttpURL httpURL = new HttpURL();
                        Toasttip tip = new Toasttip(getApplicationContext());
                        FileIO fileIO = new FileIO(getApplicationContext());
                        HashMap<String, String> requestResource = new HashMap<>();
                        requestResource.put("username", txt_id.getText().toString());
                        requestResource.put("password", txt_password.getText().toString());
                        try {
                            result = httpURL.getURLResource("api_login", "GET", requestResource);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            assert result != null;
                            if(result.get("status").equals("success")){
                                tip.showTip("登陆成功");
                                fileIO.saveCookie(result.get("token").toString());
                                if (Looper.myLooper() != Looper.getMainLooper()) {
                                    Handler mainThread = new Handler(Looper.getMainLooper());
                                    mainThread.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                                            startActivity(intent);
                                        }
                                    });
                                    return;
                                }
                            } else {
                                tip.showTip("您的账号或密码有误");
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
