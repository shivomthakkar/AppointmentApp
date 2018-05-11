package com.quicsolv.appointmentapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.quicsolv.appointmentapp.R;
import com.quicsolv.appointmentapp.activities.DashboardActivity;
import com.quicsolv.appointmentapp.adapters.SubmittedQuesListAdapter;
import com.quicsolv.appointmentapp.retrofit.RetrofitClient;
import com.quicsolv.appointmentapp.retrofit.RetrofitConstants;
import com.quicsolv.appointmentapp.retrofit.models.interfaces.QuestionnariesInterface;
import com.quicsolv.appointmentapp.retrofit.models.pojo.questionnaries.Datum;
import com.quicsolv.appointmentapp.retrofit.models.pojo.questionnaries.QuestionnariesResponse;
import com.quicsolv.appointmentapp.utils.Constants;
import com.quicsolv.appointmentapp.utils.Prefs;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SubmittedQuestionnarieFragment extends Fragment {

    private Context mContext;
    private ListView listviewQues;
    private QuestionnariesInterface questionnariesInterface;
    private ProgressBar progressQuestionnarie;
    private List<Datum> listQuestionnarie;

    public SubmittedQuestionnarieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_submitted_ques, container, false);

        mContext = getActivity();
        ((DashboardActivity) getActivity()).setToolBarTitle("Questionnaire");
        questionnariesInterface = RetrofitClient.getClient(RetrofitConstants.BASE_URL).create(QuestionnariesInterface.class);


        getIds(view);
        fetchQuestionnarieFromAPI();
        return view;
    }

    private void getIds(View view) {
        listviewQues = (ListView) view.findViewById(R.id.listview_questionnaries);
        progressQuestionnarie = (ProgressBar) view.findViewById(R.id.progress_questionnarie);

        listQuestionnarie = new ArrayList<>();
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

                    SubmittedQuesListAdapter customAdapter = new SubmittedQuesListAdapter(mContext, R.layout.row_appointment_history, listQuestionnarie);
                    listviewQues.setAdapter(customAdapter);
                    progressQuestionnarie.setVisibility(View.GONE);


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
