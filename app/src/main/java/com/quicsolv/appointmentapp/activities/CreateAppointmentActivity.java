package com.quicsolv.appointmentapp.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.quicsolv.appointmentapp.R;
import com.quicsolv.appointmentapp.retrofit.RetrofitClient;
import com.quicsolv.appointmentapp.retrofit.RetrofitConstants;
import com.quicsolv.appointmentapp.retrofit.models.interfaces.CreateAppointmentInterface;
import com.quicsolv.appointmentapp.retrofit.models.interfaces.GetSpecialityInterface;
import com.quicsolv.appointmentapp.retrofit.models.pojo.createappointment.CreateAppointmentResponse;
import com.quicsolv.appointmentapp.retrofit.models.pojo.getspeciality.ApsList;
import com.quicsolv.appointmentapp.retrofit.models.pojo.getspeciality.GetSpecialityResponse;
import com.quicsolv.appointmentapp.utils.Prefs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateAppointmentActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private GetSpecialityInterface getSpecialityInterface;
    private CreateAppointmentInterface createAppointmentInterface;
    private Spinner spinnerSpeciality;
    private EditText edttxtDescription;
    private CheckBox cbNeedImmidiate;
    private EditText edttxtDate, edttxtTime;
    private Button btnCreateAppointment;
    Calendar myCalendar = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener selectedStartDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_appointment);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mContext = CreateAppointmentActivity.this;
        getSpecialityInterface = RetrofitClient.getClient(RetrofitConstants.BASE_URL).create(GetSpecialityInterface.class);
        createAppointmentInterface = RetrofitClient.getClient(RetrofitConstants.BASE_URL).create(CreateAppointmentInterface.class);

        getIds();
        fetchSpeciality();
    }


    private void getIds() {
        spinnerSpeciality = (Spinner) findViewById(R.id.spinner_speciality);
        edttxtDescription = (EditText) findViewById(R.id.edttxt_description);
        cbNeedImmidiate = (CheckBox) findViewById(R.id.cb_need_immidiate);

        edttxtDate = (EditText) findViewById(R.id.edttxt_date);
        edttxtDate.setOnClickListener(this);

        edttxtTime = (EditText) findViewById(R.id.edttxt_time);
        edttxtTime.setOnClickListener(this);

        btnCreateAppointment = (Button) findViewById(R.id.btn_create_appointment);
        btnCreateAppointment.setOnClickListener(this);

        selectedStartDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateLabel();
            }
        };

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_create_appointment:
                createNewAppointment();
                break;
            case R.id.edttxt_date:
                new DatePickerDialog(mContext, R.style.DialogTheme, selectedStartDate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.edttxt_time:
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        boolean isPM = (hourOfDay >= 12);
                        edttxtTime.setText(String.format("%02d:%02d %s", (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12, minute, isPM ? "PM" : "AM"));
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

                break;
        }
    }

    private void createNewAppointment() {
        createAppointmentInterface.createNewAppointment(Prefs.getSharedPreferenceString(mContext, Prefs.PREF_PID, ""), "1", "test desc", "", "", "1").enqueue(new Callback<CreateAppointmentResponse>() {
            @Override
            public void onResponse(Call<CreateAppointmentResponse> call, Response<CreateAppointmentResponse> response) {
                Log.d("", "");
            }

            @Override
            public void onFailure(Call<CreateAppointmentResponse> call, Throwable t) {
                Log.d("", "");
            }
        });
    }

    private void updateDateLabel() {
        String myFormat = "MM-dd-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edttxtDate.setText(sdf.format(myCalendar.getTime()));
        edttxtDate.setError(null);
    }
}
