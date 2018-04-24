package com.quicsolv.appointmentapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quicsolv.appointmentapp.R;
import com.quicsolv.appointmentapp.activities.DashboardActivity;
import com.quicsolv.appointmentapp.utils.Prefs;


public class ProfileFragment extends Fragment {

    private Context mContext;
    private TextView mTxt_uname;
    private TextView mTxt_name;
    private TextView mTxt_mob_no;
    private TextView mTxt_email;
    private TextView mTxt_gender;
    private TextView mTxt_dob;

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

        getIds(view);
        setData();

        return view;
    }


    private void getIds(View view) {
        mTxt_uname = (TextView) view.findViewById(R.id.txt_uname);
        mTxt_name = (TextView) view.findViewById(R.id.txt_name);
        mTxt_mob_no = (TextView) view.findViewById(R.id.txt_mob_no);
        mTxt_email = (TextView) view.findViewById(R.id.txt_email);
        mTxt_gender = (TextView) view.findViewById(R.id.txt_gender);
        mTxt_dob = (TextView) view.findViewById(R.id.txt_dob);
    }

    private void setData() {
        mTxt_uname.setText(Prefs.getSharedPreferenceString(mContext, Prefs.PREF_PATIENT_NAME, ""));
        mTxt_name.setText(Prefs.getSharedPreferenceString(mContext, Prefs.PREF_PATIENT_NAME, ""));
        mTxt_mob_no.setText(Prefs.getSharedPreferenceString(mContext, Prefs.PREF_PATIENT_PHONE, ""));
        mTxt_email.setText(Prefs.getSharedPreferenceString(mContext, Prefs.PREF_PATIENT_EMAIL, ""));

        String genderId = Prefs.getSharedPreferenceString(mContext, Prefs.PREF_PATIENT_GENDER, "");
        String gender = "";
        if (genderId.equals("1")) {
            gender = "Male";
        } else {
            gender = "FeMale";
        }
        mTxt_gender.setText(gender);
        mTxt_dob.setText(Prefs.getSharedPreferenceString(mContext, Prefs.PREF_PATIENT_DOB, ""));
    }
}
