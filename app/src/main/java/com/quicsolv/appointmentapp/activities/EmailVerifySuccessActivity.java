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

public class EmailVerifySuccessActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private Button btnProceed;
    private TextView txtMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verify_success);

        mContext = EmailVerifySuccessActivity.this;

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        getIds();
    }

    private void getIds() {
        btnProceed = (Button) findViewById(R.id.btn_proceed);
        btnProceed.setOnClickListener(this);

        txtMsg = (TextView) findViewById(R.id.txt_msg);
        if (getIntent() != null && getIntent().getExtras() != null) {
            txtMsg.setText(getIntent().getExtras().getString("EmailSuccessMessage"));
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_proceed:
                Intent mainIntent = new Intent(mContext, NewQuestionariesActivity.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(mainIntent);
                break;
        }
    }
}
