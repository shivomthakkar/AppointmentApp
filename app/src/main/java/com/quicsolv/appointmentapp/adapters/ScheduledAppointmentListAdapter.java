package com.quicsolv.appointmentapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.quicsolv.appointmentapp.R;
import com.quicsolv.appointmentapp.retrofit.models.pojo.appointmentlist._1;
import com.quicsolv.appointmentapp.utils.Prefs;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**********************************************************************
 * Created by   -  Tushar Patil
 * Organization -  QuicSolv Technologies Pvt.Ltd
 * Date         -  23 Apr 2018
 ***********************************************************************/

public class ScheduledAppointmentListAdapter extends ArrayAdapter<_1> {

    private Context mContext;

    public ScheduledAppointmentListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ScheduledAppointmentListAdapter(Context context, int resource, List<_1> items) {
        super(context, resource, items);
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.row_appointment_history, null);
        }

        _1 p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.txt_doctor_name);
            TextView tt2 = (TextView) v.findViewById(R.id.txt_sp_name);
            TextView tt3 = (TextView) v.findViewById(R.id.txt_apt_date);
            TextView tt4 = (TextView) v.findViewById(R.id.txt_apt_time);
            ImageView doctorProfileImage = (ImageView) v.findViewById(R.id.doctor_profile_image);

            if (tt1 != null && p.getDName() != null) {
                tt1.setText("Dr. " + p.getDName().toString());
            }

            if (tt2 != null && p.getSpName() != null) {
                tt2.setText(p.getSpName());
            }

            if (tt3 != null && p.getAppitDate() != null) {
                Date parsed = null;
                String outputDate = "";

                SimpleDateFormat df_input = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
                SimpleDateFormat df_output = new SimpleDateFormat("MM-dd-yyyy", java.util.Locale.getDefault());

                try {
                    parsed = df_input.parse(p.getAppitDate());
                    outputDate = df_output.format(parsed);
                } catch (ParseException e) {

                }

                tt3.setText(outputDate);
            }

            if (tt4 != null && p.getAppitDate() != null) {
                tt4.setText(p.getAppitTime());
            }

            if (p.getDPpPath() != null) {
                String strProfile = Prefs.getSharedPreferenceString(mContext, Prefs.PREF_DOCTOR_PROFILE_IMAGE_BASE_URL, "") + p.getDPpPath();
                Picasso.with(mContext).load(strProfile).error(R.drawable.profile).into(doctorProfileImage);
            }
        }

        return v;
    }

}
