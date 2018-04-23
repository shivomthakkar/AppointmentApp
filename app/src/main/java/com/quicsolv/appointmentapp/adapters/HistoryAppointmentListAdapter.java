package com.quicsolv.appointmentapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.quicsolv.appointmentapp.R;
import com.quicsolv.appointmentapp.retrofit.models.pojo.appointmentlist._3;

import java.util.List;

/**********************************************************************
 * Created by   -  Tushar Patil
 * Organization -  QuicSolv Technologies Pvt.Ltd
 * Date         -  23 Apr 2018
 ***********************************************************************/

public class HistoryAppointmentListAdapter extends ArrayAdapter<_3> {

    public HistoryAppointmentListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public HistoryAppointmentListAdapter(Context context, int resource, List<_3> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.row_appointment_history, null);
        }

        _3 p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.txt_doctor_name);
            TextView tt2 = (TextView) v.findViewById(R.id.txt_sp_name);
            TextView tt3 = (TextView) v.findViewById(R.id.txt_apt_date);
            TextView tt4 = (TextView) v.findViewById(R.id.txt_apt_time);

            if (tt1 != null) {
                tt1.setText(p.getDName().toString());
            }

            if (tt2 != null) {
                tt2.setText(p.getSpName());
            }

            if (tt3 != null) {
                tt3.setText(p.getAppitDate());
            }

            if (tt4 != null) {
                tt4.setText(p.getAppitTime());
            }
        }

        return v;
    }

}
