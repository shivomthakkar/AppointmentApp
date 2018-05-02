package com.quicsolv.appointmentapp.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.quicsolv.appointmentapp.R;
import com.quicsolv.appointmentapp.activities.DashboardActivity;
import com.quicsolv.appointmentapp.retrofit.RetrofitClient;
import com.quicsolv.appointmentapp.retrofit.RetrofitConstants;
import com.quicsolv.appointmentapp.retrofit.models.interfaces.GetPatientProfiletInterface;
import com.quicsolv.appointmentapp.retrofit.models.interfaces.UpdatePatientProfileInterface;
import com.quicsolv.appointmentapp.retrofit.models.pojo.patientprofile.Data;
import com.quicsolv.appointmentapp.retrofit.models.pojo.patientprofile.GetPatientProfileResponse;
import com.quicsolv.appointmentapp.retrofit.models.pojo.patientprofile.UpdatePatientProfileResponse;
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


public class ProfileFragment extends Fragment implements View.OnClickListener {

    private Context mContext;
    private TextView mTxt_uname;
    private EditText mTxt_name;
    private EditText mTxt_mob_no;
    private EditText mTxt_email;
    private EditText mTxt_dob;
    private TextView btnEdit;
    private Button btnUpdateProfile;
    private GetPatientProfiletInterface getPatientProfiletInterface;
    private UpdatePatientProfileInterface updatePatientProfileInterface;
    Calendar myCalendar = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener selectedStartDate;
    private ProgressBar progressBar;
    private RadioGroup rgGender;
    private RadioButton rbGenderType;
    private RadioButton rbMale, rbFemale;
    private LinearLayout layoutGender;
    private TextView txtGender;


    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ((DashboardActivity) getActivity()).setToolBarTitle("My Profile");

        mContext = getActivity();
        getPatientProfiletInterface = RetrofitClient.getClient(RetrofitConstants.BASE_URL).create(GetPatientProfiletInterface.class);
        updatePatientProfileInterface = RetrofitClient.getClient(RetrofitConstants.BASE_URL).create(UpdatePatientProfileInterface.class);


        getIds(view);

        progressBar.setVisibility(View.VISIBLE);
        layoutGender.setVisibility(View.GONE);
        txtGender.setVisibility(View.VISIBLE);

        fetchPatientProfileData();

        return view;
    }

    private void fetchPatientProfileData() {
        getPatientProfiletInterface.getPatientDetails(Prefs.getSharedPreferenceString(mContext, Prefs.PREF_PID, "")).enqueue(new Callback<GetPatientProfileResponse>() {
            @Override
            public void onResponse(Call<GetPatientProfileResponse> call, Response<GetPatientProfileResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response != null && response.body() != null && response.body().getCode() == Constants.ERROR_CODE_200) {
                    setData(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<GetPatientProfileResponse> call, Throwable t) {

            }
        });
    }


    private void getIds(View view) {
        mTxt_uname = (TextView) view.findViewById(R.id.txt_uname);
        mTxt_name = (EditText) view.findViewById(R.id.txt_name);
        mTxt_mob_no = (EditText) view.findViewById(R.id.txt_mob_no);
        mTxt_email = (EditText) view.findViewById(R.id.txt_email);
        mTxt_dob = (EditText) view.findViewById(R.id.txt_dob);
        txtGender = (EditText) view.findViewById(R.id.txt_gender);
        layoutGender = (LinearLayout) view.findViewById(R.id.layout_gender);

        progressBar = (ProgressBar) view.findViewById(R.id.progress_profile);

        rgGender = (RadioGroup) view.findViewById(R.id.rg_gender);
        rbMale = (RadioButton) view.findViewById(R.id.rb_male);
        rbFemale = (RadioButton) view.findViewById(R.id.rb_female);


        mTxt_dob.setOnClickListener(this);


        mTxt_name.setFocusable(false);
        mTxt_name.setFocusableInTouchMode(false);
        mTxt_name.setClickable(false);

        mTxt_mob_no.setFocusable(false);
        mTxt_mob_no.setFocusableInTouchMode(false);
        mTxt_mob_no.setClickable(false);

        btnEdit = (TextView) view.findViewById(R.id.btn_edit);
        btnEdit.setOnClickListener(this);

        btnUpdateProfile = (Button) view.findViewById(R.id.btn_update_profile);
        btnUpdateProfile.setOnClickListener(this);

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
    }

    private void setData(Data data) {
        mTxt_uname.setText(data.getPName());
        mTxt_name.setText(data.getPName());
        mTxt_mob_no.setText(data.getPPhone());
        mTxt_email.setText(data.getPEmail());


        layoutGender.setVisibility(View.GONE);
        txtGender.setVisibility(View.VISIBLE);

        String genderId = data.getGender();
        if (genderId.equals("1")) {
            rgGender.check(R.id.rb_male);
            txtGender.setText("Male");
        } else {
            rgGender.check(R.id.rb_female);
            txtGender.setText("Female");
        }

        String date = data.getDob();
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat output = new SimpleDateFormat("MM-dd-yyyy");
        try {
            Date oneWayTripDate = input.parse(date);                 // parse input
            mTxt_dob.setText(output.format(oneWayTripDate));// format output


            String[] str = mTxt_dob.getText().toString().split("-");
            myCalendar.set(Integer.parseInt(str[2]), (Integer.parseInt(str[0]) - 1), Integer.parseInt(str[1]));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ((DashboardActivity) getActivity()).setNavDrawerUserName(data.getPName().toString());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update_profile:
                if (mTxt_name.getText().toString().trim().equals("")) {
                    mTxt_name.setError("Enter full name");
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    updateProfileData();
                }
                break;
            case R.id.txt_dob:
                if (btnEdit.getText().toString().equalsIgnoreCase("Cancel")) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, R.style.DialogTheme, selectedStartDate, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH));
                    myCalendar.set(myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                    datePickerDialog.show();
                }
                break;
            case R.id.btn_edit:
                if (btnEdit.getText().toString().equalsIgnoreCase("Edit")) {
                    btnUpdateProfile.setVisibility(View.VISIBLE);
                    btnEdit.setText("Cancel");

                    layoutGender.setVisibility(View.VISIBLE);
                    txtGender.setVisibility(View.GONE);

                    mTxt_name.setFocusable(true);
                    mTxt_name.setFocusableInTouchMode(true);
                    mTxt_name.setClickable(true);

                    mTxt_mob_no.setFocusable(true);
                    mTxt_mob_no.setFocusableInTouchMode(true);
                    mTxt_mob_no.setClickable(true);

                } else {
                    btnUpdateProfile.setVisibility(View.GONE);
                    btnEdit.setText("Edit");

                    layoutGender.setVisibility(View.GONE);
                    txtGender.setVisibility(View.VISIBLE);

                    mTxt_name.setFocusable(false);
                    mTxt_name.setFocusableInTouchMode(false);
                    mTxt_name.setClickable(false);

                    mTxt_mob_no.setFocusable(false);
                    mTxt_mob_no.setFocusableInTouchMode(false);
                    mTxt_mob_no.setClickable(false);
                }
                break;
        }
    }

    private void updateProfileData() {

        progressBar.setVisibility(View.GONE);

        String strCurrentDate = mTxt_dob.getText().toString();
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


        int selectedGender = 1;
        int selectedId = rgGender.getCheckedRadioButtonId();
        rbGenderType = (RadioButton) getActivity().findViewById(selectedId);

        if (rbGenderType.getText().toString().equalsIgnoreCase("male")) {
            selectedGender = 1;
        } else if (rbGenderType.getText().toString().equalsIgnoreCase("female")) {
            selectedGender = 2;
        }


        updatePatientProfileInterface.updatePatientProfile(Prefs.getSharedPreferenceString(mContext, Prefs.PREF_PID, ""),
                mTxt_name.getText().toString(), selectedGender + "", dob, mTxt_mob_no.getText().toString()).enqueue(new Callback<UpdatePatientProfileResponse>() {
            @Override
            public void onResponse(Call<UpdatePatientProfileResponse> call, Response<UpdatePatientProfileResponse> response) {
                if (response != null && response.body() != null && response.body().getCode() == Constants.ERROR_CODE_200) {
                    Toast.makeText(mContext, "Profile successfully updated", Toast.LENGTH_SHORT).show();
                    btnUpdateProfile.setVisibility(View.GONE);
                    btnEdit.setText("Edit");
                    progressBar.setVisibility(View.VISIBLE);
                    fetchPatientProfileData();

                    mTxt_name.setFocusable(false);
                    mTxt_name.setFocusableInTouchMode(false);
                    mTxt_name.setClickable(false);

                    mTxt_mob_no.setFocusable(false);
                    mTxt_mob_no.setFocusableInTouchMode(false);
                    mTxt_mob_no.setClickable(false);
                }
            }

            @Override
            public void onFailure(Call<UpdatePatientProfileResponse> call, Throwable t) {

            }
        });
    }

    private void updateDOBDateLabel() {
        String myFormat = "MM-dd-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mTxt_dob.setText(sdf.format(myCalendar.getTime()));
        mTxt_dob.setError(null);
    }
}
