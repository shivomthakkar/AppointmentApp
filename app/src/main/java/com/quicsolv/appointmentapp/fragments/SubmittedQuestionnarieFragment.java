package com.quicsolv.appointmentapp.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quicsolv.appointmentapp.R;
import com.quicsolv.appointmentapp.activities.LoginActivity;
import com.quicsolv.appointmentapp.utils.Prefs;


public class SubmittedQuestionnarieFragment extends Fragment {

    private SharedPreferences sharedpreferences;

    public SubmittedQuestionnarieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_submitted_ques, container, false);


        return view;
    }
}
