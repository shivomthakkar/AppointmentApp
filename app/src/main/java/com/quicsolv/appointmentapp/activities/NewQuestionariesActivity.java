package com.quicsolv.appointmentapp.activities;


import android.app.DatePickerDialog;
import android.content.Context;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.quicsolv.appointmentapp.R;
import com.quicsolv.appointmentapp.retrofit.RetrofitClient;
import com.quicsolv.appointmentapp.retrofit.RetrofitConstants;
import com.quicsolv.appointmentapp.retrofit.models.interfaces.QuestionnarieListWithOptionsInterface;
import com.quicsolv.appointmentapp.retrofit.models.interfaces.QuestionnariesInterface;
import com.quicsolv.appointmentapp.retrofit.models.interfaces.SaveSingleQuestionnarieInterface;
import com.quicsolv.appointmentapp.retrofit.models.interfaces.SubmitQuesAnsInterface;
import com.quicsolv.appointmentapp.retrofit.models.pojo.questionnariewithoptions.Datum;
import com.quicsolv.appointmentapp.retrofit.models.pojo.questionnariewithoptions.GetListOfQuestionnarieWithOptions;
import com.quicsolv.appointmentapp.retrofit.models.pojo.questionnariewithoptions.Option;
import com.quicsolv.appointmentapp.retrofit.models.pojo.questionnariewithoptions.Option_;
import com.quicsolv.appointmentapp.retrofit.models.pojo.questionnariewithoptions.SubQuestion;
import com.quicsolv.appointmentapp.retrofit.models.pojo.savesinglequesonserver.SingleQuestionnarieResponse;
import com.quicsolv.appointmentapp.utils.Constants;
import com.quicsolv.appointmentapp.utils.Prefs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewQuestionariesActivity extends FragmentActivity {

    private Context mContext;
    MyPageAdapter pageAdapter;
    private QuestionnariesInterface questionnariesInterface;
    private QuestionnarieListWithOptionsInterface questionnarieListWithOptionsInterface;
    private SubmitQuesAnsInterface submitQuesAnsInterface;
    private List<Datum> listQuestionnarie;
    private ProgressBar progressQuestionnarie;
    private Button btnBack, btnNextQuestion;
    private ViewPager pager;
    private static List<Datum> questionnarieNewListObject;
    private SaveSingleQuestionnarieInterface saveSingleQuestionnarieInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);

        mContext = NewQuestionariesActivity.this;

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        questionnariesInterface = RetrofitClient.getClient(RetrofitConstants.BASE_URL).create(QuestionnariesInterface.class);
        questionnarieListWithOptionsInterface = RetrofitClient.getClient(RetrofitConstants.BASE_URL).create(QuestionnarieListWithOptionsInterface.class);
        submitQuesAnsInterface = RetrofitClient.getClient(RetrofitConstants.BASE_URL).create(SubmitQuesAnsInterface.class);
        saveSingleQuestionnarieInterface = RetrofitClient.getClient(RetrofitConstants.BASE_URL).create(SaveSingleQuestionnarieInterface.class);
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
//
                    btnNextQuestion.setText("Next question");
//
                    if (pager.getCurrentItem() == listQuestionnarie.size()) {
                        btnNextQuestion.setVisibility(View.VISIBLE);
                    }
//
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

//                if (isOption1Selected || isOption2Selected || isOption3Selected || isOption4Selected) {
//
//                    if (pager.getCurrentItem() == listQuestionnarie.size() - 1 && questionnarieNewListObject.get(pager.getCurrentItem()).getPAnswer() == null) {
//                        Toast.makeText(mContext, getString(R.string.answer_selection_required), Toast.LENGTH_SHORT).show();
//                        return;
//                    }

                saveSingleQuestionnarieToServer(questionnarieNewListObject.get(0));

                if (btnNextQuestion.getText().toString().trim().equalsIgnoreCase("Finish")) {
                    questionnarieNewListObject.get(0);
////                        saveQuestionnarieToServer();
                } else {
                    btnNextQuestion.setText("Next question");
                }
//
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
//                } else {
//                    pager.setCurrentItem(pager.getCurrentItem());
//                    if (pager.getCurrentItem() == 0) {
//                        btnBack.setVisibility(View.GONE);
//                    } else {
//                        btnBack.setVisibility(View.VISIBLE);
//                    }
//                    Toast.makeText(mContext, getString(R.string.answer_selection_required), Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }

    private void fetchQuestionnarieFromAPI() {
        progressQuestionnarie.setVisibility(View.VISIBLE);
        questionnarieListWithOptionsInterface.getAllQuestionsWithOptions(Prefs.getSharedPreferenceString(mContext, Prefs.PREF_PID, "")).enqueue(new Callback<GetListOfQuestionnarieWithOptions>() {
            @Override
            public void onResponse(Call<GetListOfQuestionnarieWithOptions> call, Response<GetListOfQuestionnarieWithOptions> response) {
                progressQuestionnarie.setVisibility(View.GONE);
                if (response != null && response.body().getCode() == Constants.ERROR_CODE_200) {
                    //success
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        Datum datum = response.body().getData().get(i);
                        datum.setQaId("0");
                        datum.setAnswer("");
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
            public void onFailure(Call<GetListOfQuestionnarieWithOptions> call, Throwable t) {
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


    public static class QuestionnarieFragments extends Fragment {

        public static final String DATUM = "DATUM";
        public static final String CURRENT_PAGE = "CURRENT_PAGE";
        public static final String TOTAL_QUESTIONS = "TOTAL_QUESTIONS";
        private TextView txtQuestion;
        private RadioGroup rgQue;
        private RadioButton rbOption1, rbOption2, rbOption3, rbOption4;
        private EditText layoutDescriptive, layoutDate;
        private LinearLayout layoutDropdown;
        private Spinner spinnerOptions;
        Calendar myCalendar = Calendar.getInstance();
        Calendar myCalendarSubQue = Calendar.getInstance();
        private DatePickerDialog.OnDateSetListener selectedStartDate, selectedStartDateSubQue;
        private LinearLayout layoutCheckbox;
        private LinearLayout layoutMultiLevel;
        private LinearLayout layoutMultiLevelSubQue;
        private String selectedQueOpId;
        private Datum parentDatum;
        private RadioGroup rg;

        private TextView txtSubQuestion;
        private EditText layoutSubQueDescriptive, layoutSubQueDate;
        private LinearLayout layoutSubQueDropdown;
        private Spinner spinnerSubQueOptions;
        private LinearLayout layoutSubQueCheckbox;

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

            final View v = inflater.inflate(R.layout.ques_runtimefragment_layout, container, false);

            txtQuestion = (TextView) v.findViewById(R.id.txt_question);
            layoutDescriptive = (EditText) v.findViewById(R.id.layout_descriptive);
            layoutDate = (EditText) v.findViewById(R.id.layout_date);
            layoutDropdown = (LinearLayout) v.findViewById(R.id.layout_dropdown);
            spinnerOptions = (Spinner) v.findViewById(R.id.spinner_option);
            layoutCheckbox = (LinearLayout) v.findViewById(R.id.layout_checkbox);
            layoutMultiLevel = (LinearLayout) v.findViewById(R.id.layout_multi_level);
            layoutMultiLevelSubQue = (LinearLayout) v.findViewById(R.id.layout_multi_level_sub_que);
            rg = (RadioGroup) v.findViewById(R.id.radio_group);

            //sub question layout
            txtSubQuestion = (TextView) v.findViewById(R.id.txt_sub_question);
            layoutSubQueDescriptive = (EditText) v.findViewById(R.id.layout_sub_descriptive);
            layoutSubQueDate = (EditText) v.findViewById(R.id.layout_sub_date);
            layoutSubQueDropdown = (LinearLayout) v.findViewById(R.id.layout_sub_dropdown);
            spinnerSubQueOptions = (Spinner) v.findViewById(R.id.spinner_sub_option);
            layoutSubQueCheckbox = (LinearLayout) v.findViewById(R.id.layout_sub_checkbox);


            selectedStartDate = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    updateDateLabel(layoutDate, myCalendar);
                }
            };

            layoutDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.DialogTheme, selectedStartDate, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                    datePickerDialog.show();
                }
            });

            txtQuestion.setText("Q." + curPage + "  " + datum.getQuestion());
            questionnarieNewListObject.get(curPage - 1).setQaId("0");
            questionnarieNewListObject.get(curPage - 1).setAnswer("");

            if (datum.getQtId().trim().equals("1")) { //Question type is Descriptive

                layoutDescriptive.setVisibility(View.VISIBLE);
                layoutDate.setVisibility(View.GONE);
                layoutDropdown.setVisibility(View.GONE);
                layoutCheckbox.setVisibility(View.GONE);
                layoutMultiLevel.setVisibility(View.GONE);
                layoutMultiLevelSubQue.setVisibility(View.GONE);
                questionnarieNewListObject.get(curPage - 1).setAnswer(layoutDescriptive.getText().toString());

            } else if (datum.getQtId().trim().equals("2")) { //Question type is Datepicker

                layoutDescriptive.setVisibility(View.GONE);
                layoutDate.setVisibility(View.VISIBLE);
                layoutDropdown.setVisibility(View.GONE);
                layoutCheckbox.setVisibility(View.GONE);
                layoutMultiLevel.setVisibility(View.GONE);
                layoutMultiLevelSubQue.setVisibility(View.GONE);
                questionnarieNewListObject.get(curPage - 1).setAnswer(layoutDate.toString());

            } else if (datum.getQtId().trim().equals("3")) { //Question type is Dropdown

                layoutDescriptive.setVisibility(View.GONE);
                layoutDate.setVisibility(View.GONE);
                layoutDropdown.setVisibility(View.VISIBLE);
                layoutCheckbox.setVisibility(View.GONE);
                layoutMultiLevel.setVisibility(View.GONE);
                layoutMultiLevelSubQue.setVisibility(View.GONE);

                List<Option> optionsList = new ArrayList<>();
                for (int i = 0; i < datum.getOptions().size(); i++) {
                    optionsList.add(datum.getOptions().get(i));
                }

                ArrayAdapter<Option> adapter =
                        new ArrayAdapter<Option>(getActivity(), R.layout.speciality_spinner_item, optionsList);
                adapter.setDropDownViewResource(R.layout.speciality_spinner_item);
                spinnerOptions.setAdapter(adapter);

                questionnarieNewListObject.get(curPage - 1).setAnswer(spinnerOptions.getSelectedItem().toString());

            } else if (datum.getQtId().trim().equals("4")) { //Question type is Checkbox (Multiple selection)

                layoutDescriptive.setVisibility(View.GONE);
                layoutDate.setVisibility(View.GONE);
                layoutDropdown.setVisibility(View.GONE);
                layoutCheckbox.setVisibility(View.VISIBLE);
                layoutMultiLevel.setVisibility(View.GONE);
                layoutMultiLevelSubQue.setVisibility(View.GONE);

                final ArrayList<String> srtArr = new ArrayList<>();
                final String cbSelectedValues = "";

                for (int i = 0; i < datum.getOptions().size(); i++) {
                    final CheckBox cbOption = new CheckBox(this.getContext());
                    cbOption.setPadding(30, 30, 30, 30);
                    cbOption.setText(datum.getOptions().get(i).getQOption());
                    cbOption.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
                    cbOption.setTextSize(18);
                    layoutCheckbox.addView(cbOption);

                    cbOption.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            srtArr.add(cbOption.getText().toString());
                        }
                    });
                }

                questionnarieNewListObject.get(curPage - 1).setAnswer(cbSelectedValues);

            } else if (datum.getQtId().trim().equals("5")) { //Question type is Radio Button (Single selection)

                layoutDescriptive.setVisibility(View.GONE);
                layoutDate.setVisibility(View.GONE);
                layoutDropdown.setVisibility(View.GONE);
                layoutCheckbox.setVisibility(View.VISIBLE);
                layoutMultiLevel.setVisibility(View.GONE);
                layoutMultiLevelSubQue.setVisibility(View.GONE);


                final RadioButton[] rb = new RadioButton[datum.getOptions().size()];
                RadioGroup rg = new RadioGroup(getActivity());
                rg.setOrientation(RadioGroup.VERTICAL);
                for (int i = 0; i < datum.getOptions().size(); i++) {
                    rb[i] = new RadioButton(getActivity());
                    rb[i].setText(datum.getOptions().get(i).getQOption());
                    rb[i].setPadding(30, 30, 30, 30);
                    rb[i].setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
                    rb[i].setTextSize(18);
                    rg.addView(rb[i]);
                }
                layoutCheckbox.addView(rg);


            } else if (datum.getQtId().trim().equals("6")) { //Question type is multi level question.

                layoutDescriptive.setVisibility(View.GONE);
                layoutDate.setVisibility(View.GONE);
                layoutDropdown.setVisibility(View.GONE);
                layoutCheckbox.setVisibility(View.GONE);
                layoutMultiLevel.setVisibility(View.VISIBLE);
                layoutMultiLevelSubQue.setVisibility(View.GONE);

                selectedQueOpId = datum.getSelectedQoId();
                parentDatum = datum;

                final RadioButton[] rb = new RadioButton[datum.getOptions().size()];
//                RadioGroup rg = new RadioGroup(getActivity());
//                rg.setOrientation(RadioGroup.VERTICAL);
                for (int i = 0; i < datum.getOptions().size(); i++) {
                    rb[i] = new RadioButton(getActivity());
                    rb[i].setText(datum.getOptions().get(i).getQOption());
                    rb[i].setPadding(30, 30, 30, 30);
                    rb[i].setTag(datum.getOptions().get(i).getQoId());
                    rb[i].setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
                    rb[i].setTextSize(18);
                    rg.addView(rb[i]);
                }
//                layoutMultiLevel.addView(rg);
                rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        int i = group.getCheckedRadioButtonId();
                        RadioButton radioButton = (RadioButton) v.findViewById(checkedId);
                        String selectedId = radioButton.getTag().toString().trim();
                        if (selectedQueOpId.trim().equals(selectedId)) {
                            layoutMultiLevelSubQue.setVisibility(View.VISIBLE);
                            handleSubQuestionLayout(parentDatum.getSubQuestion());
                        } else {
                            layoutMultiLevelSubQue.setVisibility(View.GONE);
                        }
                    }
                });
            }


            return v;
        }

        private void handleSubQuestionLayout(SubQuestion subQuestion) {

            selectedStartDateSubQue = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    myCalendarSubQue.set(Calendar.YEAR, year);
                    myCalendarSubQue.set(Calendar.MONTH, monthOfYear);
                    myCalendarSubQue.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    updateDateLabel(layoutSubQueDate, myCalendarSubQue);
                }
            };

            layoutSubQueDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.DialogTheme, selectedStartDateSubQue, myCalendarSubQue
                            .get(Calendar.YEAR), myCalendarSubQue.get(Calendar.MONTH),
                            myCalendarSubQue.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                    datePickerDialog.show();
                }
            });


            txtSubQuestion.setText("Q." + "  " + subQuestion.getQuestion());

            if (subQuestion.getQtId().trim().equals("1")) { //Question type is Descriptive

                layoutSubQueDescriptive.setVisibility(View.VISIBLE);
                layoutSubQueDate.setVisibility(View.GONE);
                layoutSubQueDropdown.setVisibility(View.GONE);
                layoutSubQueCheckbox.setVisibility(View.VISIBLE);

            } else if (subQuestion.getQtId().trim().equals("2")) { //Question type is Datepicker

                layoutSubQueDescriptive.setVisibility(View.GONE);
                layoutSubQueDate.setVisibility(View.VISIBLE);
                layoutSubQueDropdown.setVisibility(View.GONE);
                layoutSubQueCheckbox.setVisibility(View.VISIBLE);

            } else if (subQuestion.getQtId().trim().equals("3")) { //Question type is Dropdown

                layoutSubQueDescriptive.setVisibility(View.GONE);
                layoutSubQueDate.setVisibility(View.GONE);
                layoutSubQueDropdown.setVisibility(View.VISIBLE);
                layoutSubQueCheckbox.setVisibility(View.VISIBLE);

                List<Option_> optionsList = new ArrayList<>();
                for (int i = 0; i < subQuestion.getOptions().size(); i++) {
                    optionsList.add(subQuestion.getOptions().get(i));
                }

                ArrayAdapter<Option_> adapter =
                        new ArrayAdapter<Option_>(getActivity(), R.layout.speciality_spinner_item, optionsList);
                adapter.setDropDownViewResource(R.layout.speciality_spinner_item);
                spinnerSubQueOptions.setAdapter(adapter);

            } else if (subQuestion.getQtId().trim().equals("4")) { //Question type is Checkbox (Multiple selection)

                layoutSubQueDescriptive.setVisibility(View.GONE);
                layoutSubQueDate.setVisibility(View.GONE);
                layoutSubQueDropdown.setVisibility(View.GONE);
                layoutSubQueCheckbox.setVisibility(View.VISIBLE);
                layoutSubQueCheckbox.removeAllViews();


                for (int i = 0; i < subQuestion.getOptions().size(); i++) {
                    CheckBox cbOption = new CheckBox(this.getContext());
                    cbOption.setPadding(30, 30, 30, 30);
                    cbOption.setText(subQuestion.getOptions().get(i).getQOption());
                    cbOption.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
                    cbOption.setTextSize(18);
                    layoutSubQueCheckbox.addView(cbOption);
                }

            } else if (subQuestion.getQtId().trim().equals("5")) { //Question type is Radio Button (Single selection)

                layoutSubQueDescriptive.setVisibility(View.GONE);
                layoutSubQueDate.setVisibility(View.GONE);
                layoutSubQueDropdown.setVisibility(View.GONE);
                layoutSubQueCheckbox.setVisibility(View.VISIBLE);
                layoutSubQueCheckbox.removeAllViews();

                final RadioButton[] rb = new RadioButton[subQuestion.getOptions().size()];
                RadioGroup rg = new RadioGroup(getActivity());
                rg.setOrientation(RadioGroup.VERTICAL);
                for (int i = 0; i < subQuestion.getOptions().size(); i++) {
                    rb[i] = new RadioButton(getActivity());
                    rb[i].setText(subQuestion.getOptions().get(i).getQOption());
                    rb[i].setPadding(30, 30, 30, 30);
                    rb[i].setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
                    rb[i].setTextSize(18);
                    rg.addView(rb[i]);
                }
                layoutSubQueCheckbox.addView(rg);


            }
        }

        private void updateDateLabel(EditText edtxtDate, Calendar calendar) {
            String myFormat = "MM-dd-yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            edtxtDate.setText(sdf.format(calendar.getTime()));
            edtxtDate.setError(null);
        }
    }

    public void saveSingleQuestionnarieToServer(Datum queDatum) {
        if (!queDatum.getQtId().equals("6")) {
            saveSingleQuestionnarieInterface.saveQuesOnServer(Prefs.getSharedPreferenceString(mContext, Prefs.PREF_PID, ""),
                    queDatum.getQId(), queDatum.getQtId(), queDatum.getAnswer(), queDatum.getQaId(),
                    queDatum.getSubQuestion().getQId(), queDatum.getSubQuestion().getQtId(), "sub_ques_ans", "").enqueue(new Callback<SingleQuestionnarieResponse>() {
                @Override
                public void onResponse(Call<SingleQuestionnarieResponse> call, Response<SingleQuestionnarieResponse> response) {
                    Log.d("", "");
                }

                @Override
                public void onFailure(Call<SingleQuestionnarieResponse> call, Throwable t) {
                    Log.d("", "");
                }
            });
        } else {
            saveSingleQuestionnarieInterface.saveQuesOnServer(Prefs.getSharedPreferenceString(mContext, Prefs.PREF_PID, ""),
                    queDatum.getQId(), queDatum.getQtId(), "ansbytushar", "1",
                    "", "", "sub_ques_ans", "").enqueue(new Callback<SingleQuestionnarieResponse>() {
                @Override
                public void onResponse(Call<SingleQuestionnarieResponse> call, Response<SingleQuestionnarieResponse> response) {
                    Log.d("", "");
                }

                @Override
                public void onFailure(Call<SingleQuestionnarieResponse> call, Throwable t) {
                    Log.d("", "");
                }
            });
        }
    }
}
