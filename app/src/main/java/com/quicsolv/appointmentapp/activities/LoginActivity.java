package com.quicsolv.appointmentapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.quicsolv.appointmentapp.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private TextView txtFrogotPswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = LoginActivity.this;
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        getIds();
    }

    private void getIds() {
        txtFrogotPswd = (TextView) findViewById(R.id.txt_forgot_pswd);
        txtFrogotPswd.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_forgot_pswd:
                Intent forgotPswdIntent = new Intent(mContext, ForgotPswdActivity.class);
                startActivity(forgotPswdIntent);
                break;
        }
    }
}
