package com.quicsolv.appointmentapp.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.quicsolv.appointmentapp.R;
import com.quicsolv.appointmentapp.retrofit.RetrofitClient;
import com.quicsolv.appointmentapp.retrofit.RetrofitConstants;
import com.quicsolv.appointmentapp.retrofit.models.interfaces.QuestionnariesInterface;
import com.quicsolv.appointmentapp.retrofit.models.pojo.questionnaries.Datum;
import com.quicsolv.appointmentapp.retrofit.models.pojo.questionnaries.QuestionnariesResponse;
import com.quicsolv.appointmentapp.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionariesActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private Button btnBack, btnNextQuestion;
    private TextView txtQuestion;
    private RadioGroup rgQue;
    private RadioButton rbOption1, rbOption2, rbOption3, rbOption4;
    private QuestionnariesInterface questionnariesInterface;
    private ProgressBar progressQuestionnarie;
    private List<Datum> listQuestionnarie;
    private int questionnarieSize = 0;
    private int currentPage = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionaries);

        mContext = QuestionariesActivity.this;

        questionnariesInterface = RetrofitClient.getClient(RetrofitConstants.BASE_URL).create(QuestionnariesInterface.class);
        listQuestionnarie = new ArrayList<>();

        getIds();
        fetchQuestionnarieFromAPI();
    }

    private void getIds() {
        rgQue = (RadioGroup) findViewById(R.id.rg_que);
        rbOption1 = (RadioButton) findViewById(R.id.option1);
        rbOption2 = (RadioButton) findViewById(R.id.option2);
        rbOption3 = (RadioButton) findViewById(R.id.option3);
        rbOption4 = (RadioButton) findViewById(R.id.option4);

        txtQuestion = (TextView) findViewById(R.id.txt_question);
        progressQuestionnarie = (ProgressBar) findViewById(R.id.progress_questionnarie);

        btnBack = (Button) findViewById(R.id.btn_back);
        btnNextQuestion = (Button) findViewById(R.id.btn_next_que);
        btnBack.setOnClickListener(this);
        btnNextQuestion.setOnClickListener(this);

        if (currentPage == 0) {
            btnBack.setVisibility(View.GONE);
        } else {
            btnBack.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                if (currentPage != 0) {

                    btnNextQuestion.setText("Next question");

                    if (questionnarieSize != 0 && currentPage == questionnarieSize - 1) {
                        btnNextQuestion.setVisibility(View.VISIBLE);
                    }

                    if (currentPage == 1) {
                        btnBack.setVisibility(View.GONE);
                        btnNextQuestion.setVisibility(View.VISIBLE);
                    }
                    currentPage -= 1;
                    updateQuestionnarie();
                }
                break;

            case R.id.btn_next_que:
                btnBack.setVisibility(View.VISIBLE);

                if (questionnarieSize != 0 && currentPage != questionnarieSize) {
                    if (currentPage == questionnarieSize - 2) {
                        btnNextQuestion.setVisibility(View.GONE);
                    }

                    if (currentPage + 2 == questionnarieSize) {
                        btnBack.setVisibility(View.VISIBLE);
                        btnNextQuestion.setVisibility(View.VISIBLE);
                        btnNextQuestion.setText("Finish");
                    }

                    currentPage += 1;
                    updateQuestionnarie();
                }
                break;
        }
    }

    private void fetchQuestionnarieFromAPI() {
        progressQuestionnarie.setVisibility(View.VISIBLE);
        questionnariesInterface.getQuestionnarie("3").enqueue(new Callback<QuestionnariesResponse>() {
            @Override
            public void onResponse(Call<QuestionnariesResponse> call, Response<QuestionnariesResponse> response) {
                Log.d("", "");
                progressQuestionnarie.setVisibility(View.GONE);
                if (response != null && response.body().getCode() == Constants.ERROR_CODE_200) {
                    //success
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        Datum datum = response.body().getData().get(i);
                        listQuestionnarie.add(datum);
                    }

                    updateQuestionnarie();
                } else {
                    //failure
                    Toast.makeText(mContext, "Please try again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<QuestionnariesResponse> call, Throwable t) {
                Log.d("", "");
            }
        });
    }

    private void updateQuestionnarie() {
        questionnarieSize = listQuestionnarie.size();
        txtQuestion.setText("Q." + (currentPage + 1) + "  " + listQuestionnarie.get(currentPage).getQuestion());

        rbOption1.setText(listQuestionnarie.get(currentPage).getOption1());
        rbOption2.setText(listQuestionnarie.get(currentPage).getOption2());
        rbOption3.setText(listQuestionnarie.get(currentPage).getOption3());
        rbOption4.setText(listQuestionnarie.get(currentPage).getOption4());
    }
}
