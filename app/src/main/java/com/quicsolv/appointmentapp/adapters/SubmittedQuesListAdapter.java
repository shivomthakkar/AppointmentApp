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


        ViewHolderItem viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolderItem();
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            convertView = vi.inflate(R.layout.row_submitted_ques, null);

            viewHolder.txtQue = (TextView) convertView.findViewById(R.id.txt_que);
            viewHolder.op1 = (TextView) convertView.findViewById(R.id.txt_op1);
//            viewHolder.op2 = (TextView) convertView.findViewById(R.id.txt_op2);
//            viewHolder.op3 = (TextView) convertView.findViewById(R.id.txt_op3);
//            viewHolder.op4 = (TextView) convertView.findViewById(R.id.txt_op4);

//            Datum p = getItem(position);
//            if (p.getPAnswer() != null && p.getPAnswer().equals("1")) {
//                viewHolder.op1.setBackgroundColor(mContext.getResources().getColor(R.color.splash_color));
//            } else if (p.getPAnswer() != null && p.getPAnswer().equals("2")) {
//                viewHolder.op2.setBackgroundColor(mContext.getResources().getColor(R.color.splash_color));
//            } else if (p.getPAnswer() != null && p.getPAnswer().equals("3")) {
//                viewHolder.op3.setBackgroundColor(mContext.getResources().getColor(R.color.splash_color));
//            } else if (p.getPAnswer() != null && p.getPAnswer().equals("4")) {
//                viewHolder.op4.setBackgroundColor(mContext.getResources().getColor(R.color.splash_color));
//            }

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderItem) convertView.getTag();
        }


        Datum p = getItem(position);

        if (p != null) {
            if (viewHolder.txtQue != null && p.getQuestion() != null) {
                viewHolder.txtQue.setText(p.getQuestion().toString());
            }

            if (p.getPAnswer() != null) {

                if (p.getPAnswer().equals("1")) {
                    if (viewHolder.op1 != null && p.getOption1() != null) {
                        viewHolder.op1.setText(p.getOption1().toString());
                    }
                }

                if (p.getPAnswer().equals("2")) {
                    if (viewHolder.op1 != null && p.getOption2() != null) {
                        viewHolder.op1.setText(p.getOption2().toString());
                    }
                }

                if (p.getPAnswer().equals("3")) {
                    if (viewHolder.op1 != null && p.getOption3() != null) {
                        viewHolder.op1.setText(p.getOption3().toString());
                    }
                }

                if (p.getPAnswer().equals("4")) {
                    if (viewHolder.op1 != null && p.getOption4() != null) {
                        viewHolder.op1.setText(p.getOption4().toString());
                    }
                }
            }
        }

        return convertView;
    }

    static class ViewHolderItem {
        TextView txtQue;
        TextView op1;
//        TextView op2;
//        TextView op3;
//        TextView op4;
    }

}
