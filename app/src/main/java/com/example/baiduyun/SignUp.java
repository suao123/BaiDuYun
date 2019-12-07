package com.example.baiduyun;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    private EditText txt_phone;
    private EditText txt_password;
    private Button btn_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        txt_phone = findViewById(R.id.signUp_id);
        txt_password = findViewById(R.id.signUp_password);
        btn_signup = findViewById(R.id.button_signUp);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txt_phone.getText().equals("")){
                    Toast.makeText(getApplicationContext(), "账号为空", Toast.LENGTH_SHORT).show();
                } else if(txt_password.getText().equals("")){
                    Toast.makeText(getApplicationContext(), "密码为空", Toast.LENGTH_SHORT).show();
                } else if(txt_phone.getText().length() != 11){
                    Toast.makeText(getApplicationContext(), "电话号码长度不正确", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
