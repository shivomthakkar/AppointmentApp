package com.quicsolv.appointmentapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.quicsolv.appointmentapp.R;
import com.quicsolv.appointmentapp.retrofit.models.pojo.questionnaries.Datum;

import java.util.List;

/**********************************************************************
 * Created by   -  Tushar Patil
 * Organization -  QuicSolv Technologies Pvt.Ltd
 * Date         -  23 Apr 2018
 ***********************************************************************/

public class SubmittedQuesListAdapter extends ArrayAdapter<Datum> {

    private Context mContext;

    public SubmittedQuesListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public SubmittedQuesListAdapter(Context context, int resource, List<Datum> items) {
        super(context, resource, items);
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.row_submitted_ques, null);
        }

        Datum p = getItem(position);

        if (p != null) {
            TextView txtQue = (TextView) v.findViewById(R.id.txt_que);
            TextView op1 = (TextView) v.findViewById(R.id.txt_op1);
            TextView op2 = (TextView) v.findViewById(R.id.txt_op2);
            TextView op3 = (TextView) v.findViewById(R.id.txt_op3);
            TextView op4 = (TextView) v.findViewById(R.id.txt_op4);

            if (txtQue != null && p.getQuestion() != null) {
                txtQue.setText("Que " + (position + 1) + " . " + p.getQuestion().toString());
            }

            if (op1 != null && p.getOption1() != null) {
                op1.setText(p.getOption1().toString());
            }

            if (op2 != null && p.getOption2() != null) {
                op2.setText(p.getOption2());
            }

            if (op3 != null && p.getOption3() != null) {
                op3.setText(p.getOption3());
            }

            if (op4 != null && p.getOption4() != null) {
                op4.setText(p.getOption4());
            }

            if (p.getPAnswer() != null && p.getPAnswer().equals("1")) {
                op1.setBackgroundColor(mContext.getResources().getColor(R.color.splash_color));
            } else if (p.getPAnswer() != null && p.getPAnswer().equals("2")) {
                op2.setBackgroundColor(mContext.getResources().getColor(R.color.splash_color));
            } else if (p.getPAnswer() != null && p.getPAnswer().equals("3")) {
                op3.setBackgroundColor(mContext.getResources().getColor(R.color.splash_color));
            } else if (p.getPAnswer() != null && p.getPAnswer().equals("4")) {
                op4.setBackgroundColor(mContext.getResources().getColor(R.color.splash_color));
            }
        }

        return v;
    }

}
