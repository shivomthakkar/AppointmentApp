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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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


            if (p.getQtId().equals("1")) {//Descriptive

                viewHolder.mainQueLayout.setVisibility(View.VISIBLE);
                viewHolder.subQueLayout.setVisibility(View.GONE);
                if (viewHolder.op1 != null && p.getDescAns() != null) {
                    viewHolder.op1.setText(p.getDescAns().toString());
                }

            } else if (p.getQtId().equals("2")) {//Datepicker

                viewHolder.mainQueLayout.setVisibility(View.VISIBLE);
                viewHolder.subQueLayout.setVisibility(View.GONE);

                if (viewHolder.op1 != null && p.getDateAns() != null) {
                    DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                    DateFormat outputFormat = new SimpleDateFormat("MM-dd-yyyy");
                    String startDateStr = p.getDateAns().toString();
                    Date date = null;
                    String startDateStrNewFormat = "";
                    try {
                        date = inputFormat.parse(startDateStr);
                        startDateStrNewFormat = outputFormat.format(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    viewHolder.op1.setText(startDateStrNewFormat);
                }

            } else if (p.getQtId().equals("3")) {//Dropdown

                viewHolder.mainQueLayout.setVisibility(View.VISIBLE);
                viewHolder.subQueLayout.setVisibility(View.GONE);
                if (viewHolder.op1 != null && p.getSingleAns() != null) {
                    viewHolder.op1.setText(p.getSingleAns().toString());
                }

            } else if (p.getQtId().equals("4")) {//Checkbox

                viewHolder.mainQueLayout.setVisibility(View.VISIBLE);
                viewHolder.subQueLayout.setVisibility(View.GONE);
                if (viewHolder.op1 != null && p.getMultiAns() != null) {
                    viewHolder.op1.setText(p.getMultiAns().toString());
                }

            } else if (p.getQtId().equals("5")) {//Radio Button
                viewHolder.mainQueLayout.setVisibility(View.VISIBLE);
                viewHolder.subQueLayout.setVisibility(View.GONE);
                if (viewHolder.op1 != null && p.getSingleAns() != null) {
                    viewHolder.op1.setText(p.getSingleAns().toString());
                }
            } else if (p.getQtId().equals("6")) {//Second level question

                viewHolder.mainQueLayout.setVisibility(View.VISIBLE);
                viewHolder.subQueLayout.setVisibility(View.VISIBLE);
                if (viewHolder.op1 != null && p.getSingleAns() != null) {
                    viewHolder.op1.setText(p.getSingleAns().toString());
                }

                //Sub question layout
                if (viewHolder.txtSubQue != null && p.getSubQuestion().getQuestion() != null) {
                    viewHolder.txtSubQue.setText(p.getSubQuestion().getQuestion().toString());
                }

                if (p.getSubQuestion().getQtId().equals("1")) {//Descriptive
                    if (viewHolder.op1Sub != null && p.getSubQuestion().getDescAns() != null) {
                        viewHolder.op1Sub.setText(p.getSubQuestion().getDescAns().toString());
                    }
                } else if (p.getSubQuestion().getQtId().equals("2")) {//Datepicker

                    if (viewHolder.op1Sub != null && p.getSubQuestion().getDateAns() != null) {
                        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                        DateFormat outputFormat = new SimpleDateFormat("MM-dd-yyyy");
                        String startDateStr = p.getSubQuestion().getDateAns().toString();
                        Date date = null;
                        String startDateStrNewFormat = "";
                        try {
                            date = inputFormat.parse(startDateStr);
                            startDateStrNewFormat = outputFormat.format(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        viewHolder.op1Sub.setText(startDateStrNewFormat);
                    }
                } else if (p.getSubQuestion().getQtId().equals("3")) {//Dropdown
                    if (viewHolder.op1Sub != null && p.getSubQuestion().getSingleAns() != null) {
                        viewHolder.op1Sub.setText(p.getSubQuestion().getSingleAns().toString());
                    }
                } else if (p.getSubQuestion().getQtId().equals("4")) {//Checkbox
                    if (viewHolder.op1Sub != null && p.getSubQuestion().getMultiAns() != null) {
                        viewHolder.op1Sub.setText(p.getSubQuestion().getMultiAns().toString());
                    }
                } else if (p.getSubQuestion().getQtId().equals("5")) {//Radio Button
                    if (viewHolder.op1Sub != null && p.getSubQuestion().getSingleAns() != null) {
                        viewHolder.op1Sub.setText(p.getSubQuestion().getSingleAns().toString());
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
