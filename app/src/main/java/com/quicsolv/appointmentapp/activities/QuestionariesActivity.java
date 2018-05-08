package com.quicsolv.appointmentapp.activities;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.quicsolv.appointmentapp.R;
import com.quicsolv.appointmentapp.retrofit.RetrofitClient;
import com.quicsolv.appointmentapp.retrofit.RetrofitConstants;
import com.quicsolv.appointmentapp.retrofit.models.interfaces.QuestionnariesInterface;
import com.quicsolv.appointmentapp.retrofit.models.interfaces.SubmitQuesAnsInterface;
import com.quicsolv.appointmentapp.retrofit.models.pojo.questionnaries.Datum;
import com.quicsolv.appointmentapp.retrofit.models.pojo.questionnaries.QuestionnariesResponse;
import com.quicsolv.appointmentapp.retrofit.models.pojo.submitQuesAns.SubmitQuesAnsRequest;
import com.quicsolv.appointmentapp.retrofit.models.pojo.submitQuesAns.SubmitQuesAnsResponse;
import com.quicsolv.appointmentapp.utils.Constants;
import com.quicsolv.appointmentapp.utils.Prefs;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionariesActivity extends FragmentActivity {

    private Context mContext;
    MyPageAdapter pageAdapter;
    private QuestionnariesInterface questionnariesInterface;
    private SubmitQuesAnsInterface submitQuesAnsInterface;
    private List<Datum> listQuestionnarie;
    private ProgressBar progressQuestionnarie;
    private Button btnBack, btnNextQuestion;
    private ViewPager pager;
    private static List<Datum> questionnarieNewListObject;
    public static boolean isOption1Selected = false,
            isOption2Selected = false, isOption3Selected = false, isOption4Selected = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);

        mContext = QuestionariesActivity.this;

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        questionnariesInterface = RetrofitClient.getClient(RetrofitConstants.BASE_URL).create(QuestionnariesInterface.class);
        submitQuesAnsInterface = RetrofitClient.getClient(RetrofitConstants.BASE_URL).create(SubmitQuesAnsInterface.class);
        listQuestionnarie = new ArrayList<>();
        progressQuestionnarie = (ProgressBar) findViewById(R.id.progress_questionnarie);
        fetchQuestionnarieFromAPI();

        btnBack = (Button) findViewById(R.id.btn_back);
        btnNextQuestion = (Button) findViewById(R.id.btn_next_que);

        pager = (ViewPager) findViewById(R.id.viewpager);

        if (pager.getCurrentItem() == 0) {
            btnBack.setVisibility(View.GONE);
            btnNextQuestion.setText(getString(R.string.str_btn_proceed));
        } else {
            btnBack.setVisibility(View.VISIBLE);
        }


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pager.getCurrentItem() != 0) {

                    btnNextQuestion.setText("Next question");

                    if (pager.getCurrentItem() == listQuestionnarie.size()) {
                        btnNextQuestion.setVisibility(View.VISIBLE);
                    }

                    if (pager.getCurrentItem() == 1) {
                        btnBack.setVisibility(View.GONE);
                        btnNextQuestion.setVisibility(View.VISIBLE);
                        btnNextQuestion.setText(getString(R.string.str_btn_proceed));
                    }
                    pager.setCurrentItem(pager.getCurrentItem() - 1);
                } else {
                    btnNextQuestion.setText(getString(R.string.str_btn_proceed));
                }
            }
        });


        btnNextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBack.setVisibility(View.VISIBLE);

                if (isOption1Selected || isOption2Selected || isOption3Selected || isOption4Selected) {

                    if (pager.getCurrentItem() == listQuestionnarie.size() - 1 && questionnarieNewListObject.get(pager.getCurrentItem()).getPAnswer() == null) {
                        Toast.makeText(mContext, getString(R.string.answer_selection_required), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (btnNextQuestion.getText().toString().trim().equalsIgnoreCase("Finish")) {
                        questionnarieNewListObject.get(0);
                        saveQuestionnarieToServer();
                    } else {
                        btnNextQuestion.setText("Next question");
                    }

                    if (pager.getCurrentItem() != listQuestionnarie.size()) {
                        if (pager.getCurrentItem() == listQuestionnarie.size() - 2) {
                            btnNextQuestion.setVisibility(View.GONE);
                        }

                        if (pager.getCurrentItem() + 2 == listQuestionnarie.size()) {
                            btnBack.setVisibility(View.VISIBLE);
                            btnNextQuestion.setVisibility(View.VISIBLE);
                            btnNextQuestion.setText("Finish");
                        }

                        pager.setCurrentItem(pager.getCurrentItem() + 1);
                    }
                } else {
                    pager.setCurrentItem(pager.getCurrentItem());
                    if (pager.getCurrentItem() == 0) {
                        btnBack.setVisibility(View.GONE);
                    } else {
                        btnBack.setVisibility(View.VISIBLE);
                    }
                    Toast.makeText(mContext, getString(R.string.answer_selection_required), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveQuestionnarieToServer() {

        SubmitQuesAnsRequest submitQuesAnsRequest = new SubmitQuesAnsRequest();
        submitQuesAnsRequest.setPid(Integer.parseInt(Prefs.getSharedPreferenceString(mContext, Prefs.PREF_PID, "")));

        List<com.quicsolv.appointmentapp.retrofit.models.pojo.submitQuesAns.Datum> listDatum = new ArrayList<>();
        com.quicsolv.appointmentapp.retrofit.models.pojo.submitQuesAns.Datum datum = null;

        for (int i = 0; i < questionnarieNewListObject.size(); i++) {
            datum = new com.quicsolv.appointmentapp.retrofit.models.pojo.submitQuesAns.Datum();
            datum.setQid(Integer.parseInt(questionnarieNewListObject.get(i).getQid().toString()));
            datum.setQrId(0);
            datum.setPAnswer(Integer.parseInt(questionnarieNewListObject.get(i).getPAnswer().toString()));
            datum.setQAnswer(Integer.parseInt(questionnarieNewListObject.get(i).getQAnswer().toString()));
            listDatum.add(datum);
        }
        submitQuesAnsRequest.setData(listDatum);

        String dataJsnStr = new Gson().toJson(submitQuesAnsRequest);

        submitQuesAnsInterface.submitQuesAns(dataJsnStr).enqueue(new Callback<SubmitQuesAnsResponse>() {
            @Override
            public void onResponse(Call<SubmitQuesAnsResponse> call, Response<SubmitQuesAnsResponse> response) {
                Log.d("", "");
                Intent intent = new Intent(mContext, QuestionnarieCompletedActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<SubmitQuesAnsResponse> call, Throwable t) {
                Log.d("", "");
            }
        });
    }

    class MyPageAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;

        public MyPageAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return this.fragments.get(position);
        }

        @Override
        public int getCount() {
            return this.fragments.size();
        }
    }


    private List<Fragment> getFragments() {
        List<Fragment> fList = new ArrayList<Fragment>();

        int totalSize = listQuestionnarie.size();

        for (int i = 0; i < totalSize; i++) {
            int currentPage = i + 1;
            fList.add(QuestionnarieFragments.newInstance(listQuestionnarie.get(i), currentPage, totalSize));
        }
        return fList;
    }

    private void fetchQuestionnarieFromAPI() {
        progressQuestionnarie.setVisibility(View.VISIBLE);
        questionnariesInterface.getQuestionnarie(Prefs.getSharedPreferenceString(mContext, Prefs.PREF_PID, "")).enqueue(new Callback<QuestionnariesResponse>() {
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

                    questionnarieNewListObject = new ArrayList<Datum>(listQuestionnarie);

                    List<Fragment> fragments = getFragments();
                    pageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments);
                    pager = (ViewPager) findViewById(R.id.viewpager);
                    pager.setAdapter(pageAdapter);
                } else {
                    //failure
                    Toast.makeText(mContext, getString(R.string.please_try_again), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<QuestionnariesResponse> call, Throwable t) {
                Log.d("", "");
            }
        });
    }

    public static class QuestionnarieFragments extends Fragment {

        public static final String DATUM = "DATUM";
        public static final String CURRENT_PAGE = "CURRENT_PAGE";
        public static final String TOTAL_QUESTIONS = "TOTAL_QUESTIONS";
        private TextView txtQuestion;
        private RadioGroup rgQue;
        private RadioButton rbOption1, rbOption2, rbOption3, rbOption4;

        public static final QuestionnarieFragments newInstance(Datum message, int curPage, int totalSize) {
            QuestionnarieFragments f = new QuestionnarieFragments();
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

            isOption1Selected = false;
            isOption2Selected = false;
            isOption3Selected = false;
            isOption4Selected = false;

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

            rgQue.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    // checkedId is the RadioButton selected

                    switch (checkedId) {
                        case R.id.option1:
                            isOption1Selected = true;
                            questionnarieNewListObject.get(curPage - 1).setPAnswer(1);
                            break;
                        case R.id.option2:
                            isOption2Selected = true;
                            questionnarieNewListObject.get(curPage - 1).setPAnswer(2);
                            break;
                        case R.id.option3:
                            isOption3Selected = true;
                            questionnarieNewListObject.get(curPage - 1).setPAnswer(3);
                            break;
                        case R.id.option4:
                            isOption4Selected = true;
                            questionnarieNewListObject.get(curPage - 1).setPAnswer(4);
                            break;
                    }
                }
            });

            return v;
        }
    }
}
