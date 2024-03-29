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
import com.quicsolv.appointmentapp.retrofit.models.interfaces.SetNewPasswordInterface;
import com.quicsolv.appointmentapp.retrofit.models.pojo.resetpassword.SetNewPasswordResponse;
import com.quicsolv.appointmentapp.utils.Connectivity;
import com.quicsolv.appointmentapp.utils.Constants;
import com.quicsolv.appointmentapp.utils.Prefs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetNewPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private EditText edttxtEmail, edttxtNewPswd, edttxtConfirmPswd, edttxtDynamicAccessCode;
    private Button btnResetPswd;
    private SetNewPasswordInterface setNewPasswordInterface;
    private ProgressBar progressResetPswd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_password);

        mContext = SetNewPasswordActivity.this;

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setNewPasswordInterface = RetrofitClient.getClient(RetrofitConstants.BASE_URL).create(SetNewPasswordInterface.class);


        getIds();
    }

    private void getIds() {
        edttxtEmail = (EditText) findViewById(R.id.edttxt_email);
        edttxtNewPswd = (EditText) findViewById(R.id.edttxt_password);
        edttxtConfirmPswd = (EditText) findViewById(R.id.edttxt_confirm_password);
        edttxtDynamicAccessCode = (EditText) findViewById(R.id.edttxt_dymanic_access_code);
        progressResetPswd = (ProgressBar) findViewById(R.id.progress_set_new_pswd);

        String email = Prefs.getSharedPreferenceString(mContext, Prefs.PREF_PATIENT_EMAIL, "");
        if (!email.equals("")) {
            edttxtEmail.setText(email);
        } else {
            if (getIntent() != null && getIntent().getExtras() != null) {
                email = getIntent().getExtras().getString("Email");
                edttxtEmail.setText(email);
            }
        }

        btnResetPswd = (Button) findViewById(R.id.btn_reset_pswd);
        btnResetPswd.setOnClickListener(this);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_reset_pswd:

                String email = "";
                if (!edttxtEmail.getText().toString().trim().equals("")) {
                    email = edttxtEmail.getText().toString();
                    edttxtEmail.setError(null);
                } else {
                    edttxtEmail.setError(getString(R.string.enter_email_address));
                }

                String newPswd = "";
                if (!edttxtNewPswd.getText().toString().trim().equals("")) {
                    newPswd = edttxtNewPswd.getText().toString();
                    edttxtNewPswd.setError(null);
                } else {
                    edttxtNewPswd.setError(getString(R.string.enter_password));
                }

                String confirmPswd = "";
                if (!edttxtConfirmPswd.getText().toString().trim().equals("")) {
                    confirmPswd = edttxtConfirmPswd.getText().toString();
                    edttxtConfirmPswd.setError(null);
                } else {
                    edttxtConfirmPswd.setError(getString(R.string.enter_confirm_password));
                }

                String resetCode = "";
                if (!edttxtDynamicAccessCode.getText().toString().trim().equals("")) {
                    resetCode = edttxtDynamicAccessCode.getText().toString();
                    edttxtDynamicAccessCode.setError(null);
                } else {
                    edttxtDynamicAccessCode.setError(getString(R.string.enter_dynamic_access_code));
                }

                if (Connectivity.isNetworkConnected(MyApplication.getInstance())) {

                    if (newPswd.trim().equals(confirmPswd)) {
                        if (!email.equals("") && !newPswd.equals("") && !confirmPswd.equals("") && !resetCode.equals("")) {
                            progressResetPswd.setVisibility(View.VISIBLE);
                            setNewPassword(email, newPswd, resetCode);
                        } else {
                            Toast.makeText(mContext, getString(R.string.all_fields_are_required), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        edttxtConfirmPswd.setError(getString(R.string.password_does_not_match));
                    }
                } else {
                    progressResetPswd.setVisibility(View.GONE);
                    Toast.makeText(mContext, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }


    private void setNewPassword(String email, String newPswd, String resetCode) {
        setNewPasswordInterface.setNewPassword(email, newPswd, resetCode).enqueue(new Callback<SetNewPasswordResponse>() {
            @Override
            public void onResponse(Call<SetNewPasswordResponse> call, Response<SetNewPasswordResponse> response) {
                progressResetPswd.setVisibility(View.GONE);
                if (response != null && response.body() != null) {
                    if (response.body().getCode() == Constants.ERROR_CODE_200) {
                        //success
//                    Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        showSuccessAlert(getString(R.string.password_reset_successfully));
                    } else {
                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SetNewPasswordResponse> call, Throwable t) {

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
        builder.setTitle(getString(R.string.password_reset_title))
                .setMessage(message.toString())
                .setCancelable(false)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Prefs.setSharedPreferenceString(mContext, Prefs.PREF_EMAIL, "");
                        Prefs.setSharedPreferenceString(mContext, Prefs.PREF_PASSWORD, "");
                        Intent mainIntent = new Intent(mContext, LoginActivity.class);
                        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(mainIntent);
                        finish();
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
