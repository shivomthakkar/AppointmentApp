package com.quicsolv.appointmentapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.quicsolv.appointmentapp.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private Button btnLogin, btnRegister;
    private TextView txtFrogotPswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = MainActivity.this;
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        getIds();
    }


    private void getIds() {
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnRegister = (Button) findViewById(R.id.btn_register);
        txtFrogotPswd = (TextView) findViewById(R.id.txt_forgot_pswd);

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        txtFrogotPswd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                Intent loginIntent = new Intent(mContext, LoginActivity.class);
                startActivity(loginIntent);
                break;

            case R.id.btn_register:
                Intent regIntent = new Intent(mContext, RegistrationActivity.class);
                startActivity(regIntent);
                break;

            case R.id.txt_forgot_pswd:
                Intent forgotPswdIntent = new Intent(mContext, ForgotPswdActivity.class);
                startActivity(forgotPswdIntent);
                break;
        }
    }
}
