package com.quicsolv.appointmentapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.quicsolv.appointmentapp.R;

public class QuestionnarieCompletedActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private Button btnProceed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnarie_completed);

        mContext = QuestionnarieCompletedActivity.this;

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
                Intent mainIntent = new Intent(mContext, DashboardActivity.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(mainIntent);
                break;
        }
    }
}
