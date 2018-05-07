package com.quicsolv.appointmentapp.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.quicsolv.appointmentapp.R;
import com.quicsolv.appointmentapp.activities.DashboardActivity;
import com.quicsolv.appointmentapp.activities.ReportUploadActivity;
import com.quicsolv.appointmentapp.adapters.UploadedFilesListAdapter;
import com.quicsolv.appointmentapp.retrofit.RetrofitClient;
import com.quicsolv.appointmentapp.retrofit.RetrofitConstants;
import com.quicsolv.appointmentapp.retrofit.models.interfaces.UploadedReportsListInterface;
import com.quicsolv.appointmentapp.retrofit.models.pojo.uploadedfilelist.Datum;
import com.quicsolv.appointmentapp.retrofit.models.pojo.uploadedfilelist.UploadedReportsListResponse;
import com.quicsolv.appointmentapp.utils.Constants;
import com.quicsolv.appointmentapp.utils.Prefs;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReportsFragment extends Fragment implements View.OnClickListener {

    private Context mContext;
    private ListView listviewReports;
    private ProgressBar progressbar;
    private Button btnUploadDocs, btnUpload;
    private UploadedReportsListInterface uploadedReportsListInterface;
    private List<Datum> listReports;
    private LinearLayout layoutNoRecord;
    private static final int EXTERNAL_STORAGE_PERMISSION_CONSTANT = 100;
    private RelativeLayout rlToolbar;

    public ReportsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reports, container, false);

        ((DashboardActivity) getActivity()).setToolBarTitle("Reports");
        uploadedReportsListInterface = RetrofitClient.getClient(RetrofitConstants.BASE_URL).create(UploadedReportsListInterface.class);

        mContext = getActivity();

        getIds(view);

        progressbar.setVisibility(View.VISIBLE);
        getReportsList();

        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);
        }

        return view;
    }

    private void getReportsList() {
        String pid = Prefs.getSharedPreferenceString(mContext, Prefs.PREF_PID, "");
//        String pid = "1";
        uploadedReportsListInterface.getReportsList(pid).enqueue(new Callback<UploadedReportsListResponse>() {
            @Override
            public void onResponse(Call<UploadedReportsListResponse> call, Response<UploadedReportsListResponse> response) {
                progressbar.setVisibility(View.GONE);
                if (response != null && response.body().getCode() == Constants.ERROR_CODE_200) {
                    layoutNoRecord.setVisibility(View.GONE);
                    rlToolbar.setVisibility(View.VISIBLE);

                    Prefs.setSharedPreferenceString(mContext, Prefs.PREF_UPLOADED_FILE_BASE_URL, response.body().getBaseUrl());
                    if (response.body().getData() != null) {
                        listReports = response.body().getData();

                        UploadedFilesListAdapter customAdapter = new UploadedFilesListAdapter(mContext, R.layout.row_appointment_history, listReports);
                        listviewReports.setAdapter(customAdapter);
                    }
                } else {
                    layoutNoRecord.setVisibility(View.VISIBLE);
                    rlToolbar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<UploadedReportsListResponse> call, Throwable t) {

            }
        });
    }


    private void getIds(View view) {
        listviewReports = (ListView) view.findViewById(R.id.listview_reports);
        progressbar = (ProgressBar) view.findViewById(R.id.progress_reports);
        layoutNoRecord = (LinearLayout) view.findViewById(R.id.layout_no_reports);

        rlToolbar = (RelativeLayout) view.findViewById(R.id.toolbar);

        btnUploadDocs = (Button) view.findViewById(R.id.btn_upload_docs);
        btnUploadDocs.setOnClickListener(this);

        btnUpload = (Button) view.findViewById(R.id.btn_upload);
        btnUpload.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_upload_docs:
                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);
                }

                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(mContext, "No Permission", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(mContext, ReportUploadActivity.class);
                    startActivity(intent);
                }
                break;

            case R.id.btn_upload:
                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);
                }

                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(mContext, "No Permission", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(mContext, ReportUploadActivity.class);
                    startActivity(intent);
                }

                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        progressbar.setVisibility(View.VISIBLE);
        getReportsList();
    }
}
