package com.cml.spms.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cml.spms.R;
import com.cml.spms.utils.ThreadUtils;
import com.cml.spms.utils.ToastUtils;

public class LoginActivity extends AppCompatActivity {

    private final String Username = "admin";
    private final String Password = "admin";

    private EditText etInputUsername;
    private EditText etInputPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initListener();
    }

    private void initListener() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strInputUsername = etInputUsername.getText().toString();
                String strInputPassword = etInputPassword.getText().toString();
                if(strInputUsername.isEmpty()){
                    etInputUsername.setError("请输入用户名");
                    return;
                }
                if(strInputPassword.isEmpty()){
                    etInputPassword.setError("请输入密码");
                    return;
                }
                ThreadUtils.runInThread(new Runnable() {
                    @Override
                    public void run() {
                        if(strInputUsername.equals(Username)&&strInputPassword.equals(Password)){
                            ToastUtils.showToastSafe(LoginActivity.this,"登陆成功");
                            finish();
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                        }else{
                            ToastUtils.showToastSafe(LoginActivity.this,"登陆失败");
                        }
                    }
                });
            }
        });
    }

    private void initView() {
        etInputUsername = findViewById(R.id.et_input_username);
        etInputPassword = findViewById(R.id.et_input_password);
        btnLogin = findViewById(R.id.btn_login);
    }
}