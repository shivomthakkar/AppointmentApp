package com.quicsolv.appointmentapp.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.quicsolv.appointmentapp.R;
import com.quicsolv.appointmentapp.retrofit.models.pojo.appointmentlist._2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**********************************************************************
 * Created by   -  Tushar Patil
 * Organization -  QuicSolv Technologies Pvt.Ltd
 * Date         -  02 May 2018
 ***********************************************************************/

public class DialogPendingAppointmentDetails extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button ok;
    public TextView txtDoctorName, txtSpecialityName, txtPrfdate, txtPrfTime, txtDesc, txtStatus;
    public _2 param;

    public DialogPendingAppointmentDetails(Activity a, _2 param) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.param = param;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_pending_appointment_details);
        ok = (Button) findViewById(R.id.btn_ok);
        ok.setOnClickListener(this);

        txtDoctorName = (TextView) findViewById(R.id.txt_doctor_name);
        txtSpecialityName = (TextView) findViewById(R.id.txt_sp_name);
        txtPrfdate = (TextView) findViewById(R.id.txt_prf_date);
        txtPrfTime = (TextView) findViewById(R.id.txt_prf_time);
        txtDesc = (TextView) findViewById(R.id.txt_desc);
        txtStatus = (TextView) findViewById(R.id.txt_status);

        if (param.getDName() != null) {
            txtDoctorName.setText(param.getDName() + "");
        } else {
            txtDoctorName.setText("Pending status");
        }

        if (param.getSpName() != null) {
            txtSpecialityName.setText(param.getSpName() + "");
        }

        if (param.getPrfDate() != null) {
            Date parsed = null;
            String outputDate = "";

            SimpleDateFormat df_input = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
            SimpleDateFormat df_output = new SimpleDateFormat("dd MMM yyyy", java.util.Locale.getDefault());

            try {
                parsed = df_input.parse(param.getPrfDate());
                outputDate = df_output.format(parsed);
            } catch (ParseException e) {

            }

            txtPrfdate.setText(outputDate + "");
        }

        if (param.getPrfTime() != null) {
            txtPrfTime.setText(param.getPrfTime() + "");
        }

        if (param.getDescription() != null) {
            txtDesc.setText(param.getDescription() + "");
        }

        if (param.getApsName() != null) {
            txtStatus.setText(param.getApsName() + "");
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}