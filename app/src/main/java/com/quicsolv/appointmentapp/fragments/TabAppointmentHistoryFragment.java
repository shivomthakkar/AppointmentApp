package com.quicsolv.appointmentapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.quicsolv.appointmentapp.R;
import com.quicsolv.appointmentapp.activities.RequestAppointmentActivity;
import com.quicsolv.appointmentapp.adapters.HistoryAppointmentListAdapter;
import com.quicsolv.appointmentapp.dialog.DialogHistoryAppointmentDetails;
import com.quicsolv.appointmentapp.retrofit.RetrofitClient;
import com.quicsolv.appointmentapp.retrofit.RetrofitConstants;
import com.quicsolv.appointmentapp.retrofit.models.interfaces.AppointmentListInterface;
import com.quicsolv.appointmentapp.retrofit.models.pojo.appointmentlist.AppointmentListResponse;
import com.quicsolv.appointmentapp.retrofit.models.pojo.appointmentlist._3;
import com.quicsolv.appointmentapp.utils.Constants;
import com.quicsolv.appointmentapp.utils.Prefs;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**********************************************************************
 * Created by   -  Tushar Patil
 * Organization -  QuicSolv Technologies Pvt.Ltd
 * Date         -  23 Apr 2018
 ***********************************************************************/

public class TabAppointmentHistoryFragment extends Fragment implements View.OnClickListener {

    private Context mContext;
    private ListView listviewAppointmentHistory;
    private AppointmentListInterface appointmentListInterface;
    private List<_3> listHistory;
    private ProgressBar progressLogin;
    private TextView txt_no_appointment;
    private LinearLayout noAptLayout;
    private Button btnRequestAppointment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_appointment_history_fragment, container, false);

        mContext = getActivity();
        appointmentListInterface = RetrofitClient.getClient(RetrofitConstants.BASE_URL).create(AppointmentListInterface.class);

        getIds(view);
        progressLogin.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fetchAppointmentList();

            }
        }, 600);
        return view;
    }


    private void getIds(View view) {
        listviewAppointmentHistory = (ListView) view.findViewById(R.id.listview_appointment_history);
        progressLogin = (ProgressBar) view.findViewById(R.id.progress_register);
        txt_no_appointment = (TextView) view.findViewById(R.id.txt_no_appointment);
        noAptLayout = (LinearLayout) view.findViewById(R.id.layout_no_appointments);
        btnRequestAppointment = (Button) view.findViewById(R.id.btn_request_appointment);
        btnRequestAppointment.setOnClickListener(this);

        listviewAppointmentHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DialogHistoryAppointmentDetails cdd = new DialogHistoryAppointmentDetails(getActivity(), listHistory.get(position));
                cdd.show();

                Rect displayRectangle = new Rect();
                Window window = getActivity().getWindow();

                window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);

                cdd.getWindow().setLayout((int) (displayRectangle.width() *
                        1.0f), (int) (displayRectangle.height() * 1.0f));
            }
        });
    }

    private void fetchAppointmentList() {
        progressLogin.setVisibility(View.VISIBLE);

        appointmentListInterface.getAllAppointments(
                Prefs.getSharedPreferenceString(mContext, Prefs.PREF_PID, "")).enqueue(new Callback<AppointmentListResponse>() {
            @Override
            public void onResponse(Call<AppointmentListResponse> call, Response<AppointmentListResponse> response) {

                if (response != null && response.body().getCode() == Constants.ERROR_CODE_200) {

                    listHistory = new ArrayList<>();

                    if (response.body().getApList().get3() != null) {
                        listHistory = response.body().getApList().get3();

                        Prefs.setSharedPreferenceString(mContext, Prefs.PREF_DOCTOR_PROFILE_IMAGE_BASE_URL, response.body().getBaseUrl());

                        HistoryAppointmentListAdapter customAdapter = new HistoryAppointmentListAdapter(mContext, R.layout.row_appointment_history, listHistory);
                        listviewAppointmentHistory.setAdapter(customAdapter);
                        progressLogin.setVisibility(View.GONE);
                        noAptLayout.setVisibility(View.GONE);

                        // records found
                        listviewAppointmentHistory.setVisibility(View.VISIBLE);
                        txt_no_appointment.setVisibility(View.GONE);
                    } else {
                        // No records found
                        progressLogin.setVisibility(View.GONE);
                        listviewAppointmentHistory.setVisibility(View.GONE);
                        noAptLayout.setVisibility(View.VISIBLE);
                        txt_no_appointment.setVisibility(View.VISIBLE);
                    }
                } else if (response != null && response.body().getCode() == Constants.ERROR_CODE_400) {
                    //failure
                    Toast.makeText(mContext, "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
                    progressLogin.setVisibility(View.GONE);

                    // records found
                    listviewAppointmentHistory.setVisibility(View.VISIBLE);
                    txt_no_appointment.setVisibility(View.GONE);
                } else {
                    progressLogin.setVisibility(View.GONE);
                    listviewAppointmentHistory.setVisibility(View.GONE);
                    noAptLayout.setVisibility(View.VISIBLE);
                    txt_no_appointment.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<AppointmentListResponse> call, Throwable t) {
                Log.d("", "");
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_request_appointment:
//                Fragment fragment = null;
//                Class fragmentClass = null;
//                Prefs.setSharedPreferenceBoolean(mContext, Prefs.PREF_IS_FROM_REQUEST_APT, false);
//                if (Connectivity.isNetworkConnected(MyApplication.getInstance())) {
//                    fragmentClass = RequestAppointmentFragment.class;
//                } else {
//                    fragmentClass = NoInternetConnectionFragment.class;
//                }
//
//                try {
//                    fragment = (Fragment) fragmentClass.newInstance();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                // Insert the fragment by replacing any existing fragment
//                FragmentManager fragmentManager = getFragmentManager();
//                fragmentManager.beginTransaction().replace(R.id.frame_container, fragment, "").commit();
                Intent intent = new Intent(mContext, RequestAppointmentActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                break;
        }
    }
}
