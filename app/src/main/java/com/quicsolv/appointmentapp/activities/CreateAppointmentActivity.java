package com.quicsolv.appointmentapp.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    String imdit_apptmnt_count = "";
    String sp_id = "";
    Calendar myCalendar = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener selectedStartDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_appointment);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mContext = CreateAppointmentActivity.this;
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        getSpecialityInterface = RetrofitClient.getClient(RetrofitConstants.BASE_URL).create(GetSpecialityInterface.class);
        createAppointmentInterface = RetrofitClient.getClient(RetrofitConstants.BASE_URL).create(CreateAppointmentInterface.class);

        getIds();
        fetchSpeciality();
    }


    private void getIds() {
        mContext = CreateAppointmentActivity.this;

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
//                myCalendar.set(Calendar.HOUR_OF_DAY, myCalendar.getMinimum(Calendar.HOUR_OF_DAY));
//                myCalendar.set(Calendar.MINUTE, myCalendar.getMinimum(Calendar.MINUTE));
//                myCalendar.set(Calendar.SECOND, myCalendar.getMinimum(Calendar.SECOND));
//                myCalendar.set(Calendar.MILLISECOND, myCalendar.getMinimum(Calendar.MILLISECOND));
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
                    final ArrayList<ApsList> specList = new ArrayList<>();
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


                    spinnerSpeciality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                            // your code here
                            sp_id = specList.get(position).getSpId();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parentView) {
                            // your code here
                        }

                    });

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
//                new DatePickerDialog(mContext, R.style.DialogTheme, selectedStartDate, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, R.style.DialogTheme, selectedStartDate,myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
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
                }, hour, minute, false);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

                break;
        }
    }

    private void createNewAppointment() {

        String str_Date = edttxtDate.getText().toString().trim();
        String strCurrentDate = str_Date;
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
        Date newDate = null;
        String date = "";
        try {
            newDate = format.parse(strCurrentDate);
            format = new SimpleDateFormat("yyyy-MM-dd");
            date = format.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (cbNeedImmidiate.isChecked()) {
            imdit_apptmnt_count = "1";
        } else {
            imdit_apptmnt_count = "2";
        }

        createAppointmentInterface.createNewAppointment(
                Prefs.getSharedPreferenceString(mContext, Prefs.PREF_PID, ""),
                sp_id,
                edttxtDescription.getText().toString(),
                date,
                edttxtTime.getText().toString(),
                imdit_apptmnt_count).enqueue(new Callback<CreateAppointmentResponse>() {
            @Override
            public void onResponse(Call<CreateAppointmentResponse> call, Response<CreateAppointmentResponse> response) {
                Log.d("", "");

                SuccessResponse_Dialog_Create_Appointment();

            }

            @Override
            public void onFailure(Call<CreateAppointmentResponse> call, Throwable t) {
                Log.d("", "");
            }
        });
    }

    private void SuccessResponse_Dialog_Create_Appointment() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        // builder.setCancelable(false);
        builder.setTitle("Success");
        builder.setMessage("Your appointment request has been submitted successfully.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
//                        finish();
                Intent intent_dashboard_activity = new Intent(mContext, DashboardActivity.class);
                startActivity(intent_dashboard_activity);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void updateDateLabel() {
        String myFormat = "MM-dd-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edttxtDate.setText(sdf.format(myCalendar.getTime()));
        edttxtDate.setError(null);
    }
}
