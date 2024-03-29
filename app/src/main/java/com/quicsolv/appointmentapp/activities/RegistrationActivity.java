package com.quicsolv.appointmentapp.activities;

import android.app.DatePickerDialog;
import android.content.Context;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.quicsolv.appointmentapp.MyApplication;
import com.quicsolv.appointmentapp.R;
import com.quicsolv.appointmentapp.retrofit.RetrofitClient;
import com.quicsolv.appointmentapp.retrofit.RetrofitConstants;
import com.quicsolv.appointmentapp.retrofit.models.interfaces.RegisterInterface;
import com.quicsolv.appointmentapp.retrofit.models.pojo.register.RegistrationResponse;
import com.quicsolv.appointmentapp.utils.Connectivity;
import com.quicsolv.appointmentapp.utils.Constants;
import com.quicsolv.appointmentapp.utils.Prefs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**********************************************************************
 * Created by   -  Tushar Patil
 * Organization -  QuicSolv Technologies Pvt.Ltd
 * Date         -  18 Apr 2018
 ***********************************************************************/

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private EditText edttxtFullName, edttxtMobNo, edttxtPassword, edttxtEmail, edttxtDOB;
    private Button btnregister;
    private TextView txtLogin, txtHelp;
    private RadioGroup rgGender;
    private RadioButton rbGenderType;
    private RegisterInterface registerInterface;
    Calendar myCalendar = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener selectedStartDate;
    private ProgressBar progressLogin;
    private CheckBox check_show_pass;


    /**********************************************************************
     * Activity's entry method
     ***********************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mContext = RegistrationActivity.this;

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        registerInterface = RetrofitClient.getClient(RetrofitConstants.BASE_URL).create(RegisterInterface.class);

        getIds();
    }


    /**********************************************************************
     * Declare all component Id's here
     ***********************************************************************/
    private void getIds() {
        edttxtFullName = (EditText) findViewById(R.id.edttxt_full_name);
        edttxtMobNo = (EditText) findViewById(R.id.edttxt_mob_no);
        edttxtEmail = (EditText) findViewById(R.id.edttxt_email);
        edttxtPassword = (EditText) findViewById(R.id.edttxt_password);
        edttxtDOB = (EditText) findViewById(R.id.edttxt_dob);
        edttxtDOB.setOnClickListener(this);
        check_show_pass = (CheckBox) findViewById(R.id.check_show_pass);


        progressLogin = (ProgressBar) findViewById(R.id.progress_register);

        btnregister = (Button) findViewById(R.id.btn_register);
        btnregister.setOnClickListener(this);

        txtLogin = (TextView) findViewById(R.id.txt_login);
        txtLogin.setOnClickListener(this);

        rgGender = (RadioGroup) findViewById(R.id.rg_gender);

        selectedStartDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDOBDateLabel();
            }
        };


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
            case R.id.btn_register:
                String strFullName = edttxtFullName.getText().toString().trim();
                String strMobNo = edttxtMobNo.getText().toString().trim();
                String strEmail = edttxtEmail.getText().toString().trim();
                String strPswd = edttxtPassword.getText().toString().trim();
                String strDOB = edttxtDOB.getText().toString().trim();

                boolean isValidEmail = false;

                if (strFullName.equals("")) {
                    edttxtFullName.setError(getString(R.string.enter_full_name));
                }

//                if (strMobNo.equals("")) {
//                    edttxtMobNo.setError("Mobile number missing");
//                }

                if (strEmail.equals("")) {
                    edttxtEmail.setError(getString(R.string.enter_email_address));
                } else {
                    if (isValidEmail(edttxtEmail.getText().toString())) {
                        isValidEmail = true;
                    } else {
                        isValidEmail = false;
                        edttxtEmail.setError(getString(R.string.enter_correct_email_address));
                    }
                }

                if (strPswd.equals("")) {
                    edttxtPassword.setError(getString(R.string.enter_password));
                }

                if (strDOB.equals("")) {
                    edttxtDOB.setError(getString(R.string.enter_dob));
                }

                int selectedGender = 1;
                int selectedId = rgGender.getCheckedRadioButtonId();
                rbGenderType = (RadioButton) findViewById(selectedId);

                if (rbGenderType.getText().toString().equalsIgnoreCase(getString(R.string.male))) {
                    selectedGender = 1;
                } else if (rbGenderType.getText().toString().equalsIgnoreCase(getString(R.string.female))) {
                    selectedGender = 2;
                }


                if (!strFullName.equals("") && !strEmail.equals("") && isValidEmail && !strPswd.equals("") && !strDOB.equals("")) {
                    edttxtFullName.setError(null);
                    edttxtMobNo.setError(null);
                    edttxtEmail.setError(null);
                    edttxtPassword.setError(null);
                    edttxtDOB.setError(null);
                    progressLogin.setVisibility(View.VISIBLE);

                    if (Connectivity.isNetworkConnected(MyApplication.getInstance())) {
                        doRegistration(strFullName, strMobNo, strEmail, strPswd, selectedGender, strDOB);
                    } else {
                        progressLogin.setVisibility(View.GONE);
                        Toast.makeText(mContext, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case R.id.txt_login:
                Intent login_Intent = new Intent(mContext, LoginActivity.class);
                startActivity(login_Intent);
                break;

            case R.id.edttxt_dob:
//                new DatePickerDialog(mContext, R.style.DialogTheme, selectedStartDate, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, R.style.DialogTheme, selectedStartDate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
                break;
        }
    }


    /**********************************************************************
     * Made Registration api call with providing all necessary parameters
     **********************************************************************
     * @param strFullName
     * @param strMobNo
     * @param strEmail
     * @param strPswd
     * @param selectedGender
     * @param strDOB   */
    private void doRegistration(String strFullName, String strMobNo, final String strEmail, final String strPswd, int selectedGender, String strDOB) {

        String strCurrentDate = strDOB;
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
        Date newDate = null;
        String dob = "";
        try {
            newDate = format.parse(strCurrentDate);
            format = new SimpleDateFormat("yyyy-MM-dd");
            dob = format.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String deviceToken = FirebaseInstanceId.getInstance().getToken();

        registerInterface.register(strFullName, strMobNo, strEmail, strPswd, selectedGender, dob, deviceToken).enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                progressLogin.setVisibility(View.GONE);
                if (response != null && response.body() != null) {
                    if (response.body().getCode() == Constants.ERROR_CODE_200) {
                        //success
//                    Toast.makeText(mContext, "Registration Successful", Toast.LENGTH_SHORT).show();
                        Prefs.setSharedPreferenceString(mContext, Prefs.PREF_PID, response.body().getPid().toString());
                        Prefs.setSharedPreferenceString(mContext, Prefs.PREF_EMAIL, strEmail);
                        Prefs.setSharedPreferenceString(mContext, Prefs.PREF_PASSWORD, strPswd);

                        Prefs.setSharedPreferenceString(mContext, Prefs.PREF_PATIENT_NAME, response.body().getPName());
                        Prefs.setSharedPreferenceString(mContext, Prefs.PREF_PATIENT_EMAIL, response.body().getPEmail());
                        Prefs.setSharedPreferenceString(mContext, Prefs.PREF_PATIENT_GENDER, response.body().getGender());

                        if (response.body().getPPhone() != null) {
                            Prefs.setSharedPreferenceString(mContext, Prefs.PREF_PATIENT_PHONE, response.body().getPPhone());
                        }

                        if (response.body().getDob() != null) {
                            Prefs.setSharedPreferenceString(mContext, Prefs.PREF_PATIENT_DOB, response.body().getDob().toString());
                        }

                        if (response.body().getPpPath() != null && !response.body().getPpPath().toString().equals("")) {//set nav drawer profile picture
                            Prefs.setSharedPreferenceString(mContext, Prefs.PREF_PATIENT_PROFILE_IMAGE_URL_, response.body().getPpPath());
                        }else{
                            Prefs.setSharedPreferenceString(mContext, Prefs.PREF_PATIENT_PROFILE_IMAGE_URL_, "");
                        }

                        Prefs.setSharedPreferenceBoolean(mContext, Prefs.PREF_IS_EMAIL_VERIFICATION_MAIL_ALREADY_SENT, true);
                        Intent mainIntent = new Intent(mContext, RegistrationSuccessActivity.class);
                        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        mainIntent.putExtra("Message", getString(R.string.registered_success_message));
                        startActivity(mainIntent);
                    } else if (response != null && response.body().getCode() == Constants.ERROR_CODE_400) {
                        //failure
                        Toast.makeText(mContext, response.body().getMessage().toString().replace("<br>", ""), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                Log.d("FAIL_REGISTRATION", t.getMessage());
            }
        });
    }

    private void updateDOBDateLabel() {
        String myFormat = "MM-dd-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edttxtDOB.setText(sdf.format(myCalendar.getTime()));
        edttxtDOB.setError(null);
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
