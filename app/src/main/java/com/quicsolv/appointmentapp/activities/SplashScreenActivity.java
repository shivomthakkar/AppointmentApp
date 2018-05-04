package com.quicsolv.appointmentapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.quicsolv.appointmentapp.R;
import com.quicsolv.appointmentapp.utils.Prefs;

/**********************************************************************
 * Created by   -  Tushar Patil
 * Organization -  QuicSolv Technologies Pvt.Ltd
 * Date         -  18 Apr 2018
 ***********************************************************************/

public class SplashScreenActivity extends AppCompatActivity {

    /**
     * Duration of wait
     **/
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mContext = SplashScreenActivity.this;
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                String strEmail = "";
                String strPswd = "";
                strEmail = Prefs.getSharedPreferenceString(mContext, Prefs.PREF_EMAIL, "");
                strPswd = Prefs.getSharedPreferenceString(mContext, Prefs.PREF_PASSWORD, "");

                if (!strEmail.trim().equals("") && !strPswd.trim().equals("")) {
                    Intent mainIntent = new Intent(mContext, LoginActivity.class);
                    startActivity(mainIntent);
                    finish();
                } else {
                    Intent mainIntent = new Intent(mContext, MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
