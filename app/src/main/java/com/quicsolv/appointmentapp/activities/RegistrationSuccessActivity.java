package com.quicsolv.appointmentapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.quicsolv.appointmentapp.R;

public class RegistrationSuccessActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private Button btnProceed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_success);

        mContext = RegistrationSuccessActivity.this;

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        getIds();
    }


    private void getIds() {
        btnProceed = (Button) findViewById(R.id.btn_proceed);
        btnProceed.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_proceed:
                Intent mainIntent = new Intent(mContext, QuestionariesActivity.class);
                startActivity(mainIntent);
                break;
        }
    }
}
