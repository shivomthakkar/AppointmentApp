package com.quicsolv.appointmentapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.quicsolv.appointmentapp.R;
import com.quicsolv.appointmentapp.retrofit.models.pojo.appointmentlist._3;
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

public class HistoryAppointmentListAdapter extends ArrayAdapter<_3> {

    private Context mContext;


    public HistoryAppointmentListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public HistoryAppointmentListAdapter(Context context, int resource, List<_3> items) {
        super(context, resource, items);
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        ViewHolder holder;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.row_appointment_history, null);
            holder = new ViewHolder();
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        _3 p = getItem(position);

        if (p != null) {
            holder.tt1 = (TextView) v.findViewById(R.id.txt_doctor_name);
            holder.tt2 = (TextView) v.findViewById(R.id.txt_sp_name);
            holder.tt3 = (TextView) v.findViewById(R.id.txt_apt_date);
            holder.tt4 = (TextView) v.findViewById(R.id.txt_apt_time);
            ImageView doctorProfileImage = (ImageView) v.findViewById(R.id.doctor_profile_image);

            if (holder.tt1 != null && p.getDName() != null) {
                holder.tt1.setText(p.getDName().toString());
            }

            if (holder.tt2 != null && p.getSpName() != null) {
                holder.tt2.setText("Problem related to - " + p.getSpName());
            }

            if (holder.tt3 != null && p.getAppitDate() != null) {
                holder.tt3.setVisibility(View.VISIBLE);
                Date parsed = null;
                String outputDate = "";

                SimpleDateFormat df_input = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
                SimpleDateFormat df_output = new SimpleDateFormat("MM-dd-yyyy", java.util.Locale.getDefault());

                try {
                    parsed = df_input.parse(p.getAppitDate());
                    outputDate = df_output.format(parsed);
                } catch (ParseException e) {

                }

                holder.tt3.setText(outputDate);
            } else {
                holder.tt3.setVisibility(View.GONE);
                holder.tt3.setText("");
            }

            if (holder.tt4 != null && p.getAppitTime() != null) {
                holder.tt4.setVisibility(View.VISIBLE);
                holder.tt4.setText(p.getAppitTime());
            } else {
                holder.tt4.setVisibility(View.GONE);
                holder.tt4.setText("");
            }

            if (p.getDPpPath() != null) {
                String strProfile = Prefs.getSharedPreferenceString(mContext, Prefs.PREF_DOCTOR_PROFILE_IMAGE_BASE_URL, "") + p.getDPpPath();
                Picasso.with(mContext).load(strProfile).error(R.drawable.profile).into(doctorProfileImage);
            }
        }

        return v;
    }


    static class ViewHolder {
        private TextView tt1;
        private TextView tt2;
        private TextView tt3;
        private TextView tt4;
        private TextView txtDesc;
    }

}
