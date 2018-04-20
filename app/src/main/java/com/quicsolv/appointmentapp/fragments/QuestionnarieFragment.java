package com.quicsolv.appointmentapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.quicsolv.appointmentapp.R;
import com.quicsolv.appointmentapp.retrofit.models.pojo.questionnaries.Datum;

/**********************************************************************
 * Created by   -  Tushar Patil
 * Organization -  QuicSolv Technologies Pvt.Ltd
 * Date         -  20 Apr 2018
 ***********************************************************************/

public class QuestionnarieFragment extends Fragment {

    public static final String DATUM = "DATUM";
    public static final String CURRENT_PAGE = "CURRENT_PAGE";
    public static final String TOTAL_QUESTIONS = "TOTAL_QUESTIONS";
    private TextView txtQuestion;
    private RadioGroup rgQue;
    private RadioButton rbOption1, rbOption2, rbOption3, rbOption4;

    public static final QuestionnarieFragment newInstance(Datum message, int curPage, int totalSize) {
        QuestionnarieFragment f = new QuestionnarieFragment();
        Bundle bdl = new Bundle(1);
        bdl.putParcelable(DATUM, message);
        bdl.putInt(CURRENT_PAGE, curPage);
        bdl.putInt(TOTAL_QUESTIONS, totalSize);
        f.setArguments(bdl);
        return f;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Datum datum = getArguments().getParcelable(DATUM);
        final int curPage = getArguments().getInt(CURRENT_PAGE);
        final int queSize = getArguments().getInt(TOTAL_QUESTIONS);

        View v = inflater.inflate(R.layout.myfragment_layout, container, false);

        rgQue = (RadioGroup) v.findViewById(R.id.rg_que);
        rbOption1 = (RadioButton) v.findViewById(R.id.option1);
        rbOption2 = (RadioButton) v.findViewById(R.id.option2);
        rbOption3 = (RadioButton) v.findViewById(R.id.option3);
        rbOption4 = (RadioButton) v.findViewById(R.id.option4);

        txtQuestion = (TextView) v.findViewById(R.id.txt_question);

        txtQuestion.setText("Q." + curPage + "  " + datum.getQuestion());
        rbOption1.setText(datum.getOption1());
        rbOption2.setText(datum.getOption2());
        rbOption3.setText(datum.getOption3());
        rbOption4.setText(datum.getOption4());

        return v;
    }
}
