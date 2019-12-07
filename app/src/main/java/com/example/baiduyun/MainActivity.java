package com.example.baiduyun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.baiduyun.constant.Constant;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

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
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
                                            .get()
                                            .url(Constant.URL + "api_login?" + "id=" + txt_id.getText() + "password=" + txt_password.getText())
                                            .build();
                try {
                    Response response = okHttpClient.newCall(request).execute();
                    if(response.isSuccessful()){

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
