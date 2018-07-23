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


        Datum data = getItem(position);

        if (data != null) {
            if (viewHolder.txtQue != null && data.getQuestion() != null) {
                viewHolder.txtQue.setText(data.getQuestion().toString());
            }


            if (data.getQtId() != null && data.getQtId().equals("1") && data.getAns() != null && !data.getAns().trim().equals("")) {//Descriptive

                viewHolder.mainQueLayout.setVisibility(View.VISIBLE);
                viewHolder.subQueLayout.setVisibility(View.GONE);
                if (viewHolder.op1 != null && data.getAns() != null) {
                    viewHolder.op1.setText(data.getAns().toString());
                }

            } else if (data.getQtId() != null && data.getQtId().equals("2") && data.getAns() != null && !data.getAns().trim().equals("")) {//Datepicker

                viewHolder.mainQueLayout.setVisibility(View.VISIBLE);
                viewHolder.subQueLayout.setVisibility(View.GONE);

                if (viewHolder.op1 != null && data.getAns() != null) {
                    viewHolder.op1.setText(data.getAns());
                }

            } else if (data.getQtId() != null && data.getQtId().equals("3") && data.getAns() != null && !data.getAns().trim().equals("")) {//Dropdown

                viewHolder.mainQueLayout.setVisibility(View.VISIBLE);
                viewHolder.subQueLayout.setVisibility(View.GONE);
                if (viewHolder.op1 != null && data.getAns() != null) {
                    viewHolder.op1.setText(data.getAns().toString());
                }

            } else if (data.getQtId() != null && data.getQtId().equals("4") && data.getAns() != null && !data.getAns().trim().equals("")) {//Checkbox

                viewHolder.mainQueLayout.setVisibility(View.VISIBLE);
                viewHolder.subQueLayout.setVisibility(View.GONE);
                if (viewHolder.op1 != null && data.getAns() != null) {
                    viewHolder.op1.setText(data.getAns().toString());
                }

            } else if (data.getQtId() != null && data.getQtId().equals("5") && data.getAns() != null && !data.getAns().trim().equals("")) {//Radio Button
                viewHolder.mainQueLayout.setVisibility(View.VISIBLE);
                viewHolder.subQueLayout.setVisibility(View.GONE);
                if (viewHolder.op1 != null && data.getAns() != null) {
                    viewHolder.op1.setText(data.getAns().toString());
                }
            } else if (data.getQtId() != null && data.getQtId().equals("6") && data.getAns() != null && !data.getAns().trim().equals("")) {//Second level question

                viewHolder.mainQueLayout.setVisibility(View.VISIBLE);

                if (viewHolder.op1 != null && data.getAns() != null) {
                    viewHolder.op1.setText(data.getAns().toString());
                }


                if (data.getSubQuestion() != null && data.getSubQuestion().getQuestion() != null && data.getSubQuestion().getAns() != null && !data.getSubQuestion().getAns().trim().equals("")) {
                    viewHolder.subQueLayout.setVisibility(View.VISIBLE);
                    //Sub question layout
                    if (viewHolder.txtSubQue != null && data.getSubQuestion() != null && data.getSubQuestion().getQuestion() != null) {
                        viewHolder.txtSubQue.setText(data.getSubQuestion().getQuestion().toString());
                    }

                    if (data.getSubQuestion() != null && data.getSubQuestion().getQtId() != null && data.getSubQuestion().getQtId().equals("1")) {//Descriptive
                        if (viewHolder.op1Sub != null && data.getSubQuestion().getAns() != null) {
                            viewHolder.op1Sub.setText(data.getSubQuestion().getAns().toString());
                        }
                    } else if (data.getSubQuestion() != null && data.getSubQuestion().getQtId() != null && data.getSubQuestion().getQtId().equals("2")) {//Datepicker

                        if (viewHolder.op1Sub != null && data.getSubQuestion().getAns() != null) {
                            viewHolder.op1Sub.setText(data.getSubQuestion().getAns());
                        }
                    } else if (data.getSubQuestion() != null && data.getSubQuestion().getQtId() != null && data.getSubQuestion().getQtId().equals("3")) {//Dropdown
                        if (viewHolder.op1Sub != null && data.getSubQuestion().getAns() != null) {
                            viewHolder.op1Sub.setText(data.getSubQuestion().getAns().toString());
                        }
                    } else if (data.getSubQuestion() != null && data.getSubQuestion().getQtId() != null && data.getSubQuestion().getQtId().equals("4")) {//Checkbox
                        if (viewHolder.op1Sub != null && data.getSubQuestion().getAns() != null) {
                            viewHolder.op1Sub.setText(data.getSubQuestion().getAns().toString());
                        }
                    } else if (data.getSubQuestion() != null && data.getSubQuestion().getQtId() != null && data.getSubQuestion().getQtId().equals("5")) {//Radio Button
                        if (viewHolder.op1Sub != null && data.getSubQuestion().getAns() != null) {
                            viewHolder.op1Sub.setText(data.getSubQuestion().getAns().toString());
                        }
                    }
                } else {
                    viewHolder.subQueLayout.setVisibility(View.GONE);
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
