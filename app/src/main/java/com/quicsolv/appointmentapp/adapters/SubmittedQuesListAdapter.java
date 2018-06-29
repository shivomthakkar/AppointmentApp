package com.quicsolv.appointmentapp.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
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
            viewHolder.txtSubQue = (TextView) convertView.findViewById(R.id.txt_sub_que);
            viewHolder.op1Sub = (TextView) convertView.findViewById(R.id.txt_sub_op1);
            viewHolder.mainQueLayout = (CardView) convertView.findViewById(R.id.card_view_main_que);
            viewHolder.subQueLayout = (CardView) convertView.findViewById(R.id.card_view_sub_que);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderItem) convertView.getTag();
        }


        Datum p = getItem(position);

        if (p != null) {
            if (viewHolder.txtQue != null && p.getQuestion() != null) {
                viewHolder.txtQue.setText(p.getQuestion().toString());
            }


            if (p.getQtId() != null && p.getQtId().equals("1")) {//Descriptive

                viewHolder.mainQueLayout.setVisibility(View.VISIBLE);
                viewHolder.subQueLayout.setVisibility(View.GONE);
                if (viewHolder.op1 != null && p.getAns() != null) {
                    viewHolder.op1.setText(p.getAns().toString());
                }

            } else if (p.getQtId() != null && p.getQtId().equals("2")) {//Datepicker

                viewHolder.mainQueLayout.setVisibility(View.VISIBLE);
                viewHolder.subQueLayout.setVisibility(View.GONE);

                if (viewHolder.op1 != null && p.getAns() != null) {
                    viewHolder.op1.setText(p.getAns());
                }

            } else if (p.getQtId() != null && p.getQtId().equals("3")) {//Dropdown

                viewHolder.mainQueLayout.setVisibility(View.VISIBLE);
                viewHolder.subQueLayout.setVisibility(View.GONE);
                if (viewHolder.op1 != null && p.getAns() != null) {
                    viewHolder.op1.setText(p.getAns().toString());
                }

            } else if (p.getQtId() != null && p.getQtId().equals("4")) {//Checkbox

                viewHolder.mainQueLayout.setVisibility(View.VISIBLE);
                viewHolder.subQueLayout.setVisibility(View.GONE);
                if (viewHolder.op1 != null && p.getAns() != null) {
                    viewHolder.op1.setText(p.getAns().toString());
                }

            } else if (p.getQtId() != null && p.getQtId().equals("5")) {//Radio Button
                viewHolder.mainQueLayout.setVisibility(View.VISIBLE);
                viewHolder.subQueLayout.setVisibility(View.GONE);
                if (viewHolder.op1 != null && p.getAns() != null) {
                    viewHolder.op1.setText(p.getAns().toString());
                }
            } else if (p.getQtId() != null && p.getQtId().equals("6")) {//Second level question

                viewHolder.mainQueLayout.setVisibility(View.VISIBLE);
                viewHolder.subQueLayout.setVisibility(View.VISIBLE);
                if (viewHolder.op1 != null && p.getAns() != null) {
                    viewHolder.op1.setText(p.getAns().toString());
                }

                //Sub question layout
                if (viewHolder.txtSubQue != null && p.getSubQuestion() != null && p.getSubQuestion().getQuestion() != null) {
                    viewHolder.txtSubQue.setText(p.getSubQuestion().getQuestion().toString());
                }

                if (p.getSubQuestion() != null && p.getSubQuestion().getQtId() != null && p.getSubQuestion().getQtId().equals("1")) {//Descriptive
                    if (viewHolder.op1Sub != null && p.getSubQuestion().getAns() != null) {
                        viewHolder.op1Sub.setText(p.getSubQuestion().getAns().toString());
                    }
                } else if (p.getSubQuestion() != null && p.getSubQuestion().getQtId() != null && p.getSubQuestion().getQtId().equals("2")) {//Datepicker

                    if (viewHolder.op1Sub != null && p.getSubQuestion().getAns() != null) {
                        viewHolder.op1Sub.setText(p.getSubQuestion().getAns());
                    }
                } else if (p.getSubQuestion() != null && p.getSubQuestion().getQtId() != null && p.getSubQuestion().getQtId().equals("3")) {//Dropdown
                    if (viewHolder.op1Sub != null && p.getSubQuestion().getAns() != null) {
                        viewHolder.op1Sub.setText(p.getSubQuestion().getAns().toString());
                    }
                } else if (p.getSubQuestion() != null && p.getSubQuestion().getQtId() != null && p.getSubQuestion().getQtId().equals("4")) {//Checkbox
                    if (viewHolder.op1Sub != null && p.getSubQuestion().getAns() != null) {
                        viewHolder.op1Sub.setText(p.getSubQuestion().getAns().toString());
                    }
                } else if (p.getSubQuestion() != null && p.getSubQuestion().getQtId() != null && p.getSubQuestion().getQtId().equals("5")) {//Radio Button
                    if (viewHolder.op1Sub != null && p.getSubQuestion().getAns() != null) {
                        viewHolder.op1Sub.setText(p.getSubQuestion().getAns().toString());
                    }
                }
            }
        }

        return convertView;
    }

    static class ViewHolderItem {
        TextView txtQue;
        TextView op1;
        TextView txtSubQue;
        TextView op1Sub;
        CardView mainQueLayout;
        CardView subQueLayout;
    }

}
