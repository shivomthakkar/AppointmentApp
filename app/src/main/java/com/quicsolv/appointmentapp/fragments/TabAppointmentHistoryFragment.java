package com.quicsolv.appointmentapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.quicsolv.appointmentapp.R;
import com.quicsolv.appointmentapp.adapters.HistoryAppointmentListAdapter;
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

public class TabAppointmentHistoryFragment extends Fragment {

    private Context mContext;
    private ListView listviewAppointmentHistory;
    private AppointmentListInterface appointmentListInterface;
    private List<_3> listHistory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_appointment_history_fragment, container, false);

        mContext = getActivity();
        appointmentListInterface = RetrofitClient.getClient(RetrofitConstants.BASE_URL).create(AppointmentListInterface.class);

        getIds(view);
        fetchAppointmentList();

        return view;
    }


    private void getIds(View view) {
        listviewAppointmentHistory = (ListView) view.findViewById(R.id.listview_appointment_history);

    }


    private void fetchAppointmentList() {
        appointmentListInterface.getAllAppointments(Prefs.getSharedPreferenceString(mContext, Prefs.PREF_PID, "")).enqueue(new Callback<AppointmentListResponse>() {
            @Override
            public void onResponse(Call<AppointmentListResponse> call, Response<AppointmentListResponse> response) {
                if (response != null && response.body().getCode() == Constants.ERROR_CODE_200) {

                    listHistory = new ArrayList<>();

                    if (response.body().getApList().get3() != null) {

                        listHistory = response.body().getApList().get3();

                        HistoryAppointmentListAdapter customAdapter = new HistoryAppointmentListAdapter(mContext, R.layout.row_appointment_history, listHistory);
                        listviewAppointmentHistory.setAdapter(customAdapter);

                    } else if (response != null && response.body().getCode() == Constants.ERROR_CODE_400) {
                        //failure
                        Toast.makeText(mContext, "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AppointmentListResponse> call, Throwable t) {
                Log.d("", "");
            }
        });
    }
}
