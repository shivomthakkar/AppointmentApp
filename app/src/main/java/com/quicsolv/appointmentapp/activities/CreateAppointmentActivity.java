package com.quicsolv.appointmentapp.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.quicsolv.appointmentapp.R;
import com.quicsolv.appointmentapp.retrofit.RetrofitClient;
import com.quicsolv.appointmentapp.retrofit.RetrofitConstants;
import com.quicsolv.appointmentapp.retrofit.models.interfaces.GetSpecialityInterface;
import com.quicsolv.appointmentapp.retrofit.models.pojo.getspeciality.ApsList;
import com.quicsolv.appointmentapp.retrofit.models.pojo.getspeciality.GetSpecialityResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateAppointmentActivity extends AppCompatActivity {

    private Context mContext;
    private GetSpecialityInterface getSpecialityInterface;
    private Spinner spinnerSpeciality;
    private EditText edttxtDescription;
    private CheckBox cbNeedImmidiate;
    private EditText edttxtDate, edttxtTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_appointment);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mContext = CreateAppointmentActivity.this;
        getSpecialityInterface = RetrofitClient.getClient(RetrofitConstants.BASE_URL).create(GetSpecialityInterface.class);

        getIds();
        fetchSpeciality();
    }


    private void getIds() {
        spinnerSpeciality = (Spinner) findViewById(R.id.spinner_speciality);
        edttxtDescription = (EditText) findViewById(R.id.edttxt_description);
        cbNeedImmidiate = (CheckBox) findViewById(R.id.cb_need_immidiate);
        edttxtDate = (EditText) findViewById(R.id.edttxt_date);
        edttxtTime = (EditText) findViewById(R.id.edttxt_time);
    }


    private void fetchSpeciality() {
        getSpecialityInterface.getSpecialityList().enqueue(new Callback<GetSpecialityResponse>() {
            @Override
            public void onResponse(Call<GetSpecialityResponse> call, Response<GetSpecialityResponse> response) {
                if (response.body() != null) {
                    ArrayList<ApsList> specList = new ArrayList<>();
                    ApsList apsList = null;
                    if (response.body().getApsList() != null && response.body().getApsList().size() > 0) {
                        for (int i = 0; i < response.body().getApsList().size(); i++) {
                            apsList = new ApsList();
                            apsList.setSpId(response.body().getApsList().get(i).getSpId());
                            apsList.setSpName(response.body().getApsList().get(i).getSpName());
                            specList.add(apsList);
                        }
                    }

                    ArrayAdapter<ApsList> adapter =
                            new ArrayAdapter<ApsList>(getApplicationContext(), R.layout.speciality_spinner_item, specList);
                    adapter.setDropDownViewResource(R.layout.speciality_spinner_item);

                    spinnerSpeciality.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<GetSpecialityResponse> call, Throwable t) {
                Log.d("", "");
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
