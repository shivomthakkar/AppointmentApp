package com.quicsolv.appointmentapp.activities;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.quicsolv.appointmentapp.R;
import com.quicsolv.appointmentapp.fragments.QuestionnarieFragment;
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

public class QuestionariesActivity extends FragmentActivity {

    private Context mContext;
    MyPageAdapter pageAdapter;
    private QuestionnariesInterface questionnariesInterface;
    private List<Datum> listQuestionnarie;
    private ProgressBar progressQuestionnarie;
    private Button btnBack, btnNextQuestion;
    private ViewPager pager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q);

        mContext = QuestionariesActivity.this;

        questionnariesInterface = RetrofitClient.getClient(RetrofitConstants.BASE_URL).create(QuestionnariesInterface.class);
        listQuestionnarie = new ArrayList<>();
        progressQuestionnarie = (ProgressBar) findViewById(R.id.progress_questionnarie);
        fetchQuestionnarieFromAPI();

        btnBack = (Button) findViewById(R.id.btn_back);
        btnNextQuestion = (Button) findViewById(R.id.btn_next_que);

        pager = (ViewPager) findViewById(R.id.viewpager);

        if (pager.getCurrentItem() == 0) {
            btnBack.setVisibility(View.GONE);
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
                    }
                    pager.setCurrentItem(pager.getCurrentItem() - 1);
                }
            }
        });


        btnNextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBack.setVisibility(View.VISIBLE);

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
            fList.add(QuestionnarieFragment.newInstance(listQuestionnarie.get(i), currentPage, totalSize));
        }
        return fList;
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

                    List<Fragment> fragments = getFragments();
                    pageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments);
                    pager = (ViewPager) findViewById(R.id.viewpager);
                    pager.setAdapter(pageAdapter);
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
}
