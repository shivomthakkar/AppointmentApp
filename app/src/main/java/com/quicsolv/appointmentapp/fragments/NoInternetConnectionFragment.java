package com.quicsolv.appointmentapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.quicsolv.appointmentapp.R;


public class NoInternetConnectionFragment extends Fragment {

    ListView lvContacts;

    public NoInternetConnectionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_no_internet_conn, container, false);


        return view;
    }
}
