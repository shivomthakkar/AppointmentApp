package com.quicsolv.appointmentapp.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.quicsolv.appointmentapp.MyApplication;
import com.quicsolv.appointmentapp.R;
import com.quicsolv.appointmentapp.retrofit.RetrofitClient;
import com.quicsolv.appointmentapp.retrofit.RetrofitConstants;
import com.quicsolv.appointmentapp.retrofit.models.interfaces.ForgotPasswordInterface;
import com.quicsolv.appointmentapp.retrofit.models.interfaces.LoginInterface;
import com.quicsolv.appointmentapp.retrofit.models.pojo.login.LoginResponse;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private TextView txtFrogotPswd;
    private TextView txt_register;
    private EditText edttxtEmail, edttxtPassword;
    private CheckBox cbRememberMe;
    private Button btnLogin;
    private LoginInterface loginInterface;
    private ProgressBar progressLogin;
    private boolean isRememberMeIsChecked;
    private CheckBox check_show_pass;
    private ForgotPasswordInterface resetPasswordInterface;
    private RelativeLayout loginMainLayout;


    /**********************************************************************
     * Activity's entry method
     ***********************************************************************/
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = LoginActivity.this;
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        loginInterface = RetrofitClient.getClient(RetrofitConstants.BASE_URL).create(LoginInterface.class);
        resetPasswordInterface = RetrofitClient.getClient(RetrofitConstants.BASE_URL).create(ForgotPasswordInterface.class);

        getIds();
    }


    /**********************************************************************
     * Declare all component Id's here
     ***********************************************************************/
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void getIds() {
        edttxtEmail = (EditText) findViewById(R.id.edttxt_email);
        edttxtPassword = (EditText) findViewById(R.id.edttxt_password);
        check_show_pass = (CheckBox) findViewById(R.id.check_show_pass);

        txtFrogotPswd = (TextView) findViewById(R.id.txt_forgot_pswd);
        txtFrogotPswd.setOnClickListener(this);

        txt_register = (TextView) findViewById(R.id.txt_register);
        txt_register.setOnClickListener(this);

        loginMainLayout = (RelativeLayout) findViewById(R.id.login_main_layout);

        cbRememberMe = (CheckBox) findViewById(R.id.cb_remember_me);
        cbRememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isRememberMeIsChecked = isChecked;
            }
        });

        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);

        progressLogin = (ProgressBar) findViewById(R.id.progress_login);

        String strEmail = "";
        String strPswd = "";
        strEmail = Prefs.getSharedPreferenceString(mContext, Prefs.PREF_EMAIL, "");
        strPswd = Prefs.getSharedPreferenceString(mContext, Prefs.PREF_PASSWORD, "");

        if (!strEmail.trim().equals("") && !strPswd.trim().equals("")) {
            edttxtEmail.setText(strEmail);
            edttxtPassword.setText(strPswd);

            edttxtEmail.setError(null);
            edttxtPassword.setError(null);
            progressLogin.setVisibility(View.VISIBLE);
            loginMainLayout.setVisibility(View.GONE);

            if (Connectivity.isNetworkConnected(MyApplication.getInstance())) {
                doLogin(strEmail, strPswd);
            } else {
                progressLogin.setVisibility(View.GONE);
//                Toast.makeText(mContext, "No Internet Connection", Toast.LENGTH_SHORT).show();
                dialogNoInternetConnection();
            }
        } else {
            loginMainLayout.setVisibility(View.VISIBLE);
        }


        check_show_pass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    edttxtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    edttxtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
    }

    /**********************************************************************
     * OnClicklisteners for Buttons, TextView.
     ***********************************************************************/

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                String strEmail = "";
                String strPswd = "";

                strEmail = Prefs.getSharedPreferenceString(mContext, Prefs.PREF_EMAIL, "");
                strPswd = Prefs.getSharedPreferenceString(mContext, Prefs.PREF_PASSWORD, "");

                if (strEmail.trim().equals("") && strPswd.trim().equals("")) {
                    strEmail = edttxtEmail.getText().toString().trim();
                    strPswd = edttxtPassword.getText().toString().trim();
                } else {
                    edttxtEmail.setText(strEmail);
                    edttxtPassword.setText(strPswd);
                }

                if (isRememberMeIsChecked) {
                    Prefs.setSharedPreferenceString(mContext, Prefs.PREF_EMAIL, strEmail);
                    Prefs.setSharedPreferenceString(mContext, Prefs.PREF_PASSWORD, strPswd);
                }

                if (strEmail.equals("")) {
                    edttxtEmail.setError("Email required");
                }

                if (strPswd.equals("")) {
                    edttxtPassword.setError("Password required");
                }

                boolean isValidEmail = false;
                if (isValidEmail(edttxtEmail.getText().toString())) {
                    isValidEmail = true;
                } else {
                    isValidEmail = false;
                    edttxtEmail.setError("Enter correct email");
                }

                if (!strEmail.equals("") && !strPswd.equals("") && isValidEmail) {
                    edttxtEmail.setError(null);
                    edttxtPassword.setError(null);
                    progressLogin.setVisibility(View.VISIBLE);

                    if (Connectivity.isNetworkConnected(MyApplication.getInstance())) {
                        doLogin(strEmail, strPswd);
                    } else {
                        progressLogin.setVisibility(View.GONE);
                        Toast.makeText(mContext, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    edttxtEmail.setError("Enter correct email");
                }
                break;

            case R.id.txt_forgot_pswd:
                Intent forgotPswdIntent = new Intent(mContext, ForgotPswdActivity.class);
                forgotPswdIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(forgotPswdIntent);
                break;

            case R.id.txt_register:
                Intent register_Intent = new Intent(mContext, RegistrationActivity.class);
                register_Intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(register_Intent);
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
                if (response != null && response.body() != null) {
                    if (response.body().getCode() == Constants.ERROR_CODE_200) {
                        //success
//                        Toast.makeText(mContext, "Login Successful", Toast.LENGTH_SHORT).show();

                        Prefs.setSharedPreferenceString(mContext, Prefs.PREF_AUTH_TOKEN, response.body().getAuthToken());
                        Prefs.setSharedPreferenceString(mContext, Prefs.PREF_PID, response.body().getPid());
                        Prefs.setSharedPreferenceString(mContext, Prefs.PREF_QUESTION_COMPLETED, response.body().getQc());

                        Prefs.setSharedPreferenceString(mContext, Prefs.PREF_PATIENT_NAME, response.body().getPName());
                        Prefs.setSharedPreferenceString(mContext, Prefs.PREF_PATIENT_EMAIL, response.body().getPEmail());
                        Prefs.setSharedPreferenceString(mContext, Prefs.PREF_PATIENT_PHONE, response.body().getPPhone());
                        Prefs.setSharedPreferenceString(mContext, Prefs.PREF_PATIENT_GENDER, response.body().getGender());
                        Prefs.setSharedPreferenceString(mContext, Prefs.PREF_PATIENT_DOB, response.body().getDob());

                        if (response.body().getPpPath() != null && !response.body().getPpPath().toString().equals("")) {//set nav drawer profile picture
                            Prefs.setSharedPreferenceString(mContext, Prefs.PREF_PATIENT_PROFILE_IMAGE_URL_, response.body().getPpPath());
                        } else {
                            Prefs.setSharedPreferenceString(mContext, Prefs.PREF_PATIENT_PROFILE_IMAGE_URL_, "");
                        }

                        if (response.body().getIsVerified().trim().equals("1")) {
                            if (response.body().getQc().trim().equals("0")) { //Questionnarie is incomplete
                                Intent mainIntent = new Intent(mContext, EmailVerifySuccessActivity.class);
                                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                mainIntent.putExtra("EmailSuccessMessage", "You are almost done. \n\n To complete this process please proceed to questionnaire.");
                                startActivity(mainIntent);
                            } else if (Integer.parseInt(response.body().getQc().trim().toString()) > 0) { //Questionnarie completed
                                Intent mainIntent = new Intent(mContext, DashboardActivity.class);
//                                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                startActivity(mainIntent);
                            }
                        } else if (response.body().getIsVerified().trim().equals("0")) {
                            progressLogin.setVisibility(View.VISIBLE);
                            boolean isEmailVerificationMailSent = Prefs.getSharedPreferenceBoolean(mContext, Prefs.PREF_IS_EMAIL_VERIFICATION_MAIL_ALREADY_SENT, false);

                            if (!isEmailVerificationMailSent) {
                                sentVerificationMail();
                            }else{
                                Intent mainIntent = new Intent(mContext, RegistrationSuccessActivity.class);
                                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                mainIntent.putExtra("Message", "To complete this process please enter email verification code, which we sent you on your registered email address.");
                                startActivity(mainIntent);
                            }
                        }

                    } else if (response != null && response.body().getCode() == Constants.ERROR_CODE_400) {
                        //failure
                        Toast.makeText(mContext, response.body().getMessage().toString(), Toast.LENGTH_SHORT).show();
                    } else {
                        //failure
                        Prefs.setSharedPreferenceString(mContext, Prefs.PREF_EMAIL, "");
                        Prefs.setSharedPreferenceString(mContext, Prefs.PREF_PASSWORD, "");
                        Toast.makeText(mContext, "Please enter correct credentials..", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("", "");
            }
        });
    }

    private void sentVerificationMail() {
        resetPasswordInterface.forgotPassword(Prefs.getSharedPreferenceString(mContext, Prefs.PREF_EMAIL, ""), "1").enqueue(new Callback<ResetPasswordResponse>() {
            @Override
            public void onResponse(Call<ResetPasswordResponse> call, Response<ResetPasswordResponse> response) {
                progressLogin.setVisibility(View.GONE);
                if (response != null && response.body() != null) {
                    if (response.body().getCode() == Constants.ERROR_CODE_200) {
                        Prefs.setSharedPreferenceBoolean(mContext, Prefs.PREF_IS_EMAIL_VERIFICATION_MAIL_ALREADY_SENT, true);
                        Intent mainIntent = new Intent(mContext, RegistrationSuccessActivity.class);
                        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        mainIntent.putExtra("Message", "To complete this process please enter email verification code, which we sent you on your registered email address.");
                        startActivity(mainIntent);
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

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private void dialogNoInternetConnection() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("No Internet Connection.");
        builder.setMessage("Please try again.");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

}
