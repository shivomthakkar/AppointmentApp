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
import com.quicsolv.appointmentapp.activities.DashboardActivity;
import com.quicsolv.appointmentapp.activities.LoginActivity;
import com.quicsolv.appointmentapp.utils.Prefs;


public class LogoutFragment extends Fragment {

    private SharedPreferences sharedpreferences;

    public LogoutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_logout, container, false);

        ((DashboardActivity) getActivity()).setToolBarTitle(getString(R.string.header_logout));

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);

        // Setting Dialog Title
        alertDialog.setTitle(getString(R.string.header_logout));

        alertDialog.setCancelable(false);

        // Setting Dialog Message
        alertDialog.setMessage(getString(R.string.want_to_logout));

        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.ic_logout_black);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton(getString(R.string.yes_please), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Prefs.setSharedPreferenceString(getActivity(), Prefs.PREF_EMAIL, "");
                Prefs.setSharedPreferenceString(getActivity(), Prefs.PREF_PASSWORD, "");

                getActivity().finish();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                getActivity().finish();
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton(getString(R.string.no_stay_here), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();

        return view;
    }
}
