package com.quicsolv.appointmentapp.retrofit.models.pojo.questionnariewithoptions;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**********************************************************************
 * Created by   -  Tushar Patil
 * Organization -  QuicSolv Technologies Pvt.Ltd
 * Date         -  26 Jun 2018
 ***********************************************************************/

public class Datum implements Parcelable {
    @SerializedName("q_id")
    @Expose
    private String qId;
    @SerializedName("pq_id")
    @Expose
    private String pqId;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("qt_id")
    @Expose
    private String qtId;
    @SerializedName("test_name")
    @Expose
    private Object testName;
    @SerializedName("selected_qo_id")
    @Expose
    private String selectedQoId;
    @SerializedName("options")
    @Expose
    private List<Option> options = null;
    @SerializedName("sub_question")
    @Expose
    private SubQuestion subQuestion;
    @SerializedName("qa_id")
    @Expose
    private String qaId;
    @SerializedName("ans")
    @Expose
    private Object ans;
    @SerializedName("answer_by_me")
    @Expose
    private String answerbyMe;


    protected Datum(Parcel in) {
        qId = in.readString();
        pqId = in.readString();
        question = in.readString();
        qtId = in.readString();
        selectedQoId = in.readString();
        qaId = in.readString();
        ans = in.readString();
        answerbyMe = in.readString();
    }

    public static final Creator<Datum> CREATOR = new Creator<Datum>() {
        @Override
        public Datum createFromParcel(Parcel in) {
            return new Datum(in);
        }

        @Override
        public Datum[] newArray(int size) {
            return new Datum[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(qId);
        dest.writeString(pqId);
        dest.writeString(question);
        dest.writeString(qtId);
        dest.writeString(selectedQoId);
        dest.writeString(qaId);
        dest.writeString(ans.toString());
        dest.writeString(answerbyMe);
    }


    public String getQId() {
        return qId;
    }

    public void setQId(String qId) {
        this.qId = qId;
    }

    public String getPqId() {
        return pqId;
    }

    public void setPqId(String pqId) {
        this.pqId = pqId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQtId() {
        return qtId;
    }

    public void setQtId(String qtId) {
        this.qtId = qtId;
    }

    public Object getTestName() {
        return testName;
    }

    public void setTestName(Object testName) {
        this.testName = testName;
    }

    public String getSelectedQoId() {
        return selectedQoId;
    }

    public void setSelectedQoId(String selectedQoId) {
        this.selectedQoId = selectedQoId;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public SubQuestion getSubQuestion() {
        return subQuestion;
    }

    public void setSubQuestion(SubQuestion subQuestion) {
        this.subQuestion = subQuestion;
    }

    public String getQaId() {
        return qaId;
    }

    public void setQaId(String qaId) {
        this.qaId = qaId;
    }


    public Object getAns() {
        return ans;
    }

    public void setAns(Object ans) {
        this.ans = ans;
    }

    public String getAnswerByMe() {
        return answerbyMe;
    }

    public void setAnswerByMe(String ans) {
        this.answerbyMe = answerbyMe;
    }
}
