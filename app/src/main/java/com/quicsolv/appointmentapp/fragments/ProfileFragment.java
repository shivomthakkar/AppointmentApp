package com.quicsolv.appointmentapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment implements View.OnClickListener {

    private Context mContext;
    private TextView mTxt_uname;
    private EditText mTxt_name;
    private EditText mTxt_mob_no;
    private EditText mTxt_email;
    private EditText mTxt_gender;
    private EditText mTxt_dob;
    private TextView btnEdit;
    private Button btnUpdateProfile;
    private GetPatientProfiletInterface getPatientProfiletInterface;
    private UpdatePatientProfileInterface updatePatientProfileInterface;

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
        fetchPatientProfileData();

        return view;
    }

    private void fetchPatientProfileData() {
        getPatientProfiletInterface.getPatientDetails(Prefs.getSharedPreferenceString(mContext, Prefs.PREF_PID, "")).enqueue(new Callback<GetPatientProfileResponse>() {
            @Override
            public void onResponse(Call<GetPatientProfileResponse> call, Response<GetPatientProfileResponse> response) {
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
        mTxt_gender = (EditText) view.findViewById(R.id.txt_gender);
        mTxt_dob = (EditText) view.findViewById(R.id.txt_dob);


        mTxt_name.setFocusable(false);
        mTxt_name.setFocusableInTouchMode(false);
        mTxt_name.setClickable(false);

        mTxt_dob.setFocusable(false);
        mTxt_dob.setFocusableInTouchMode(false);
        mTxt_dob.setClickable(false);

        mTxt_gender.setFocusable(false);
        mTxt_gender.setFocusableInTouchMode(false);
        mTxt_gender.setClickable(false);

        btnEdit = (TextView) view.findViewById(R.id.btn_edit);
        btnEdit.setOnClickListener(this);

        btnUpdateProfile = (Button) view.findViewById(R.id.btn_update_profile);
        btnUpdateProfile.setOnClickListener(this);
    }

    private void setData(Data data) {
        mTxt_uname.setText(data.getPName());
        mTxt_name.setText(data.getPName());
        mTxt_mob_no.setText(data.getPPhone());
        mTxt_email.setText(data.getPEmail());

        String genderId = data.getGender();
        String gender = "";
        if (genderId.equals("1")) {
            gender = "Male";
        } else {
            gender = "FeMale";
        }
        mTxt_gender.setText(gender);

        String date = data.getDob();
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat output = new SimpleDateFormat("MM-dd-yyyy");
        try {
            Date oneWayTripDate = input.parse(date);                 // parse input
            mTxt_dob.setText(output.format(oneWayTripDate));// format output
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update_profile:
                updateProfileData();
                break;
            case R.id.btn_edit:
                if (btnEdit.getText().toString().equalsIgnoreCase("Edit")) {
                    btnUpdateProfile.setVisibility(View.VISIBLE);
                    btnEdit.setText("Cancel");

                    mTxt_name.setFocusable(true);
                    mTxt_name.setFocusableInTouchMode(true);
                    mTxt_name.setClickable(true);

                    mTxt_dob.setFocusable(true);
                    mTxt_dob.setFocusableInTouchMode(true);
                    mTxt_dob.setClickable(true);

                    mTxt_gender.setFocusable(true);
                    mTxt_gender.setFocusableInTouchMode(true);
                    mTxt_gender.setClickable(true);

                } else {
                    btnUpdateProfile.setVisibility(View.GONE);
                    btnEdit.setText("Edit");

                    mTxt_name.setFocusable(false);
                    mTxt_name.setFocusableInTouchMode(false);
                    mTxt_name.setClickable(false);

                    mTxt_dob.setFocusable(false);
                    mTxt_dob.setFocusableInTouchMode(false);
                    mTxt_dob.setClickable(false);

                    mTxt_gender.setFocusable(false);
                    mTxt_gender.setFocusableInTouchMode(false);
                    mTxt_gender.setClickable(false);
                }
                break;
        }
    }

    private void updateProfileData() {
        updatePatientProfileInterface.updatePatientProfile(Prefs.getSharedPreferenceString(mContext, Prefs.PREF_PID, ""),
                mTxt_name.getText().toString(), "", "").enqueue(new Callback<UpdatePatientProfileResponse>() {
            @Override
            public void onResponse(Call<UpdatePatientProfileResponse> call, Response<UpdatePatientProfileResponse> response) {

            }

            @Override
            public void onFailure(Call<UpdatePatientProfileResponse> call, Throwable t) {

            }
        });
    }
}
