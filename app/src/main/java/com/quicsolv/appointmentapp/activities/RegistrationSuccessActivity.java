package com.quicsolv.appointmentapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.quicsolv.appointmentapp.MyApplication;
import com.quicsolv.appointmentapp.R;
import com.quicsolv.appointmentapp.retrofit.RetrofitClient;
import com.quicsolv.appointmentapp.retrofit.RetrofitConstants;
import com.quicsolv.appointmentapp.retrofit.models.interfaces.ForgotPasswordInterface;
import com.quicsolv.appointmentapp.retrofit.models.interfaces.VerifyEmailInterface;
import com.quicsolv.appointmentapp.retrofit.models.pojo.resetpassword.ResetPasswordResponse;
import com.quicsolv.appointmentapp.utils.Connectivity;
import com.quicsolv.appointmentapp.utils.Constants;
import com.quicsolv.appointmentapp.utils.Prefs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationSuccessActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private Button btnProceed;
    private EditText edttxtDynamicAccessCode;
    private VerifyEmailInterface verifyEmailInterface;
    private ProgressBar progressBar;
    private TextView txtMsg;
    private TextView btnLogin, btnResend;
    private ForgotPasswordInterface resetPasswordInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_success);

        mContext = RegistrationSuccessActivity.this;

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        verifyEmailInterface = RetrofitClient.getClient(RetrofitConstants.BASE_URL).create(VerifyEmailInterface.class);
        resetPasswordInterface = RetrofitClient.getClient(RetrofitConstants.BASE_URL).create(ForgotPasswordInterface.class);

        getIds();
    }


    private void getIds() {
        btnProceed = (Button) findViewById(R.id.btn_proceed);
        btnProceed.setOnClickListener(this);

        btnLogin = (TextView) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);

        btnResend = (TextView) findViewById(R.id.btn_resend);
        btnResend.setOnClickListener(this);

        txtMsg = (TextView) findViewById(R.id.txt_msg);
        if (getIntent() != null && getIntent().getExtras() != null) {
            txtMsg.setText(getIntent().getExtras().getString("Message"));
        }

        edttxtDynamicAccessCode = (EditText) findViewById(R.id.edttxt_dymanic_access_code);
        progressBar = (ProgressBar) findViewById(R.id.progress_verify_email);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_proceed:

                String resetCode = "";
                if (!edttxtDynamicAccessCode.getText().toString().trim().equals("")) {
                    resetCode = edttxtDynamicAccessCode.getText().toString();
                    edttxtDynamicAccessCode.setError(null);
                } else {
                    edttxtDynamicAccessCode.setError(getString(R.string.enter_email_verification_code));
                }

                if (Connectivity.isNetworkConnected(MyApplication.getInstance())) {

                    if (!resetCode.equals("")) {
                        progressBar.setVisibility(View.VISIBLE);
                        verifyEmail(resetCode);
                    } else {
                        edttxtDynamicAccessCode.setError(getString(R.string.enter_email_verification_code));
                    }

                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(mContext, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btn_login:
                Intent mainIntent = new Intent(mContext, LoginActivity.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(mainIntent);
                break;
            case R.id.btn_resend:
                progressBar.setVisibility(View.VISIBLE);
                sentVerificationMail();
                break;
        }
    }

    private void verifyEmail(String resetCode) {
        verifyEmailInterface.verifyEmail(Prefs.getSharedPreferenceString(mContext, Prefs.PREF_EMAIL, ""), resetCode).enqueue(new Callback<ResetPasswordResponse>() {
            @Override
            public void onResponse(Call<ResetPasswordResponse> call, Response<ResetPasswordResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response != null && response.body() != null) {
                    if (response.body().getCode() == Constants.ERROR_CODE_200) {
                        Prefs.setSharedPreferenceBoolean(mContext, Prefs.PREF_IS_EMAIL_VERIFICATION_MAIL_ALREADY_SENT, false);
                        Intent mainIntent = new Intent(mContext, EmailVerifySuccessActivity.class);
                        mainIntent.putExtra("EmailSuccessMessage", getString(R.string.email_verified_proceed_to_questionnaire));
                        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(mainIntent);
                    } else {
                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResetPasswordResponse> call, Throwable t) {
                Log.d("", "");
            }
        });
    }

    private void sentVerificationMail() {
        resetPasswordInterface.forgotPassword(Prefs.getSharedPreferenceString(mContext, Prefs.PREF_EMAIL, ""), "1").enqueue(new Callback<ResetPasswordResponse>() {
            @Override
            public void onResponse(Call<ResetPasswordResponse> call, Response<ResetPasswordResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response != null && response.body() != null) {
                    if (response.body().getCode() == Constants.ERROR_CODE_200) {
                        Prefs.setSharedPreferenceBoolean(mContext, Prefs.PREF_IS_EMAIL_VERIFICATION_MAIL_ALREADY_SENT, true);
                        Toast.makeText(mContext, getString(R.string.verification_code_sent_on_email), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResetPasswordResponse> call, Throwable t) {

            }
        });
    }
}
