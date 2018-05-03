package com.quicsolv.appointmentapp.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.quicsolv.appointmentapp.MyApplication;
import com.quicsolv.appointmentapp.R;
import com.quicsolv.appointmentapp.activities.DashboardActivity;
import com.quicsolv.appointmentapp.retrofit.RetrofitClient;
import com.quicsolv.appointmentapp.retrofit.RetrofitConstants;
import com.quicsolv.appointmentapp.retrofit.models.interfaces.ResetPasswordInterface;
import com.quicsolv.appointmentapp.retrofit.models.pojo.resetpassword.ResetPasswordResponse;
import com.quicsolv.appointmentapp.utils.Connectivity;
import com.quicsolv.appointmentapp.utils.Constants;
import com.quicsolv.appointmentapp.utils.Prefs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ResetPasswordFragment extends Fragment implements View.OnClickListener {

    private Context mContext;
    private EditText edttxtOldPswd, edttxtNewPswd, edttxtConfirmPswd;
    private Button btnResetPswd;
    private ProgressBar progressResetPswd;
    private ResetPasswordInterface resetPasswordInterface;

    public ResetPasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reset_pswd, container, false);

        ((DashboardActivity) getActivity()).setToolBarTitle("Reset Password");

        mContext = getActivity();
        resetPasswordInterface = RetrofitClient.getClient(RetrofitConstants.BASE_URL).create(ResetPasswordInterface.class);

        getIds(view);

        return view;
    }

    private void getIds(View view) {
        edttxtOldPswd = (EditText) view.findViewById(R.id.edttxt_old_pswd);
        edttxtNewPswd = (EditText) view.findViewById(R.id.edttxt_new_pswd);
        edttxtConfirmPswd = (EditText) view.findViewById(R.id.edttxt_confirm_pswd);
        progressResetPswd = (ProgressBar) view.findViewById(R.id.progress_reset_pswd);

        btnResetPswd = (Button) view.findViewById(R.id.btn_reset_pswd);
        btnResetPswd.setOnClickListener(this);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_reset_pswd:

                String oldPswd = "";
                if (!edttxtOldPswd.getText().toString().trim().equals("")) {
                    oldPswd = edttxtOldPswd.getText().toString();
                    edttxtOldPswd.setError(null);
                } else {
                    edttxtOldPswd.setError("Enter old password");
                }

                String newPswd = "";
                if (!edttxtNewPswd.getText().toString().trim().equals("")) {
                    newPswd = edttxtNewPswd.getText().toString();
                    edttxtNewPswd.setError(null);
                } else {
                    edttxtNewPswd.setError("Enter new password");
                }

                String confirmPswd = "";
                if (!edttxtConfirmPswd.getText().toString().trim().equals("")) {
                    confirmPswd = edttxtConfirmPswd.getText().toString();
                    edttxtConfirmPswd.setError(null);
                } else {
                    edttxtConfirmPswd.setError("Enter confirm password");
                }

                if (Connectivity.isNetworkConnected(MyApplication.getInstance())) {
                    if (newPswd.trim().equals(confirmPswd)) {
                        if (!oldPswd.equals("") && !newPswd.equals("") && !confirmPswd.equals("")) {
                            progressResetPswd.setVisibility(View.VISIBLE);
                            resetPassword(oldPswd, newPswd);
                        } else {
                            Toast.makeText(mContext, "All fields are required", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        edttxtConfirmPswd.setError("Password does not");
                    }
                } else {
                    progressResetPswd.setVisibility(View.GONE);
                    Toast.makeText(mContext, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void resetPassword(String oldPswd, final String newPswd) {
        resetPasswordInterface.resetPassword(Prefs.getSharedPreferenceString(mContext, Prefs.PREF_PID, ""), oldPswd, newPswd).enqueue(new Callback<ResetPasswordResponse>() {
            @Override
            public void onResponse(Call<ResetPasswordResponse> call, Response<ResetPasswordResponse> response) {
                progressResetPswd.setVisibility(View.GONE);
                if (response != null && response.body() != null) {
                    if (response.body().getCode() == Constants.ERROR_CODE_200) {
                        Prefs.setSharedPreferenceString(mContext, Prefs.PREF_PASSWORD, newPswd);
                        dialogPswdResetSuccess(response.body().getMessage().toString());
                    } else if (response != null && response.body().getCode() == Constants.ERROR_CODE_400) {
                        Toast.makeText(mContext, response.body().getMessage().toString(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, response.body().getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResetPasswordResponse> call, Throwable t) {
                Log.d("", "");
            }
        });
    }

    private void dialogPswdResetSuccess(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Reset Password");
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Prefs.setSharedPreferenceBoolean(mContext, Prefs.PREF_IS_FROM_REQUEST_APT, false);
                Intent intent_dashboard_activity = new Intent(mContext, DashboardActivity.class);
                intent_dashboard_activity.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent_dashboard_activity);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
