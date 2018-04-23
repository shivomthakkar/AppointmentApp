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
import com.quicsolv.appointmentapp.retrofit.models.interfaces.LoginInterface;
import com.quicsolv.appointmentapp.retrofit.models.pojo.login.LoginResponse;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private TextView txtFrogotPswd;
    private EditText edttxtEmail, edttxtPassword;
    private Button btnLogin;
    private LoginInterface loginInterface;
    private ProgressBar progressLogin;


    /**********************************************************************
     * Activity's entry method
     ***********************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = LoginActivity.this;
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        loginInterface = RetrofitClient.getClient(RetrofitConstants.BASE_URL).create(LoginInterface.class);

        getIds();
    }


    /**********************************************************************
     * Declare all component Id's here
     ***********************************************************************/
    private void getIds() {
        edttxtEmail = (EditText) findViewById(R.id.edttxt_email);
        edttxtPassword = (EditText) findViewById(R.id.edttxt_password);

        txtFrogotPswd = (TextView) findViewById(R.id.txt_forgot_pswd);
        txtFrogotPswd.setOnClickListener(this);

        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);

        progressLogin = (ProgressBar) findViewById(R.id.progress_login);
    }


    /**********************************************************************
     * OnClicklisteners for Buttons, TextView.
     ***********************************************************************/
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                String strEmail = edttxtEmail.getText().toString().trim();
                String strPswd = edttxtPassword.getText().toString().trim();

                if (strEmail.equals("")) {
                    edttxtEmail.setError("Email required");
                }

                if (strPswd.equals("")) {
                    edttxtPassword.setError("Password required");
                }

                if (!strEmail.equals("") && !strPswd.equals("")) {
                    edttxtEmail.setError(null);
                    edttxtPassword.setError(null);
                    progressLogin.setVisibility(View.VISIBLE);

                    if (Connectivity.isNetworkConnected(MyApplication.getInstance())) {
                        doLogin(strEmail, strPswd);
                    } else {
                        progressLogin.setVisibility(View.GONE);
                        Toast.makeText(mContext, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }

                }
                break;

            case R.id.txt_forgot_pswd:
                Intent forgotPswdIntent = new Intent(mContext, ForgotPswdActivity.class);
                startActivity(forgotPswdIntent);
                break;
        }
    }


    /**********************************************************************
     * Login api call with providing all necessary parameters
     *********************************************************************
     * @param strEmail
     * @param strPswd*/
    private void doLogin(String strEmail, String strPswd) {
        loginInterface.login(Constants.USER_TYPE_PATIENT, strEmail, strPswd).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                progressLogin.setVisibility(View.GONE);
                if (response != null && response.body().getCode() == Constants.ERROR_CODE_200) {
                    //success
                    Toast.makeText(mContext, "Login Successful", Toast.LENGTH_SHORT).show();

                    Prefs.setSharedPreferenceString(mContext, Prefs.PREF_AUTH_TOKEN, response.body().getAuthToken());
                    Prefs.setSharedPreferenceString(mContext, Prefs.PREF_PID, response.body().getPid());
                    Prefs.setSharedPreferenceString(mContext, Prefs.PREF_QUESTION_COMPLETED, response.body().getQc());

                } else if (response != null && response.body().getCode() == Constants.ERROR_CODE_400) {
                    //failure
                    Toast.makeText(mContext, response.body().getMessage().toString(), Toast.LENGTH_SHORT).show();
                } else {
                    //failure
                    Toast.makeText(mContext, "Please enter correct credentials..", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("", "");
            }
        });
    }

}
