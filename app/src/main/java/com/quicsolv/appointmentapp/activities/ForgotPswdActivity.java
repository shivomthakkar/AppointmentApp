package com.quicsolv.appointmentapp.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.quicsolv.appointmentapp.MyApplication;
import com.quicsolv.appointmentapp.R;
import com.quicsolv.appointmentapp.retrofit.RetrofitClient;
import com.quicsolv.appointmentapp.retrofit.RetrofitConstants;
import com.quicsolv.appointmentapp.retrofit.models.interfaces.ForgotPasswordInterface;
import com.quicsolv.appointmentapp.retrofit.models.pojo.resetpassword.ResetPasswordResponse;
import com.quicsolv.appointmentapp.utils.Connectivity;
import com.quicsolv.appointmentapp.utils.Constants;
import com.quicsolv.appointmentapp.utils.Prefs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**********************************************************************
 * Created by   -  Tushar Patil
 * Organization -  QuicSolv Technologies Pvt.Ltd
 * Date         -  18 Apr 2018
 ***********************************************************************/

public class ForgotPswdActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private Button btnSendEmail;
    private EditText edttxtEmail;
    private ProgressBar progressResetPswd;
    private ForgotPasswordInterface resetPasswordInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pswd);

        mContext = ForgotPswdActivity.this;
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        resetPasswordInterface = RetrofitClient.getClient(RetrofitConstants.BASE_URL).create(ForgotPasswordInterface.class);

        getIds();

    }

    private void getIds() {
        edttxtEmail = (EditText) findViewById(R.id.edttxt_reset_pswd);
        String email = Prefs.getSharedPreferenceString(mContext, Prefs.PREF_PATIENT_EMAIL, "");
        edttxtEmail.setText(email);

        progressResetPswd = (ProgressBar) findViewById(R.id.progress_reset_pswd);

        btnSendEmail = (Button) findViewById(R.id.btn_send_email);
        btnSendEmail.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send_email:
                if (Connectivity.isNetworkConnected(MyApplication.getInstance())) {
                    String email = edttxtEmail.getText().toString();
                    if (!email.trim().equals("")) {
                        progressResetPswd.setVisibility(View.VISIBLE);
                        resetPassword(email);
                    } else {
                        edttxtEmail.setError("Please enter email id");
                    }
                } else {
                    progressResetPswd.setVisibility(View.GONE);
                    Toast.makeText(mContext, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    private void resetPassword(String email) {
        resetPasswordInterface.forgotPassword(email).enqueue(new Callback<ResetPasswordResponse>() {
            @Override
            public void onResponse(Call<ResetPasswordResponse> call, Response<ResetPasswordResponse> response) {
                progressResetPswd.setVisibility(View.GONE);
                if (response != null && response.body() != null && response.body().getCode() == Constants.ERROR_CODE_200) {
                    //success
//                    Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    showSuccessAlert(response.body().getMessage());
                } else {
                    Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResetPasswordResponse> call, Throwable t) {

            }
        });
    }

    private void showSuccessAlert(String message) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(mContext, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(mContext);
        }
        builder.setTitle("Reset Password")
                .setMessage(message.toString())
                .setCancelable(false)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent mainIntent = new Intent(mContext, SetNewPasswordActivity.class);
                        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        mainIntent.putExtra("Email", edttxtEmail.getText().toString());
                        startActivity(mainIntent);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(R.drawable.ic_password)
                .show();
    }
}
