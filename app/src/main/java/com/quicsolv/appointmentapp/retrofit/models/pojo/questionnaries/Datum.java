package com.quicsolv.appointmentapp.retrofit.models.pojo.questionnaries;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**********************************************************************
 * Created by   -  Tushar Patil
 * Organization -  QuicSolv Technologies Pvt.Ltd
 * Date         -  19 Apr 2018
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
    private String testName;
    @SerializedName("selected_qo_id")
    @Expose
    private String selectedQoId;
    @SerializedName("desc_ans")
    @Expose
    private String descAns;
    @SerializedName("date_ans")
    @Expose
    private String dateAns;
    @SerializedName("single_ans")
    @Expose
    private String singleAns;
    @SerializedName("multi_ans")
    @Expose
    private String multiAns;
    @SerializedName("options")
    @Expose
    private List<Option> options = null;
    @SerializedName("sub_question")
    @Expose
    private SubQuestion subQuestion;

    protected Datum(Parcel in) {
        qId = in.readString();
        pqId = in.readString();
        question = in.readString();
        qtId = in.readString();
        testName = in.readString();
        selectedQoId = in.readString();
        descAns = in.readString();
        dateAns = in.readString();
        singleAns = in.readString();
        multiAns = in.readString();
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

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getSelectedQoId() {
        return selectedQoId;
    }

    public void setSelectedQoId(String selectedQoId) {
        this.selectedQoId = selectedQoId;
    }

    public String getDescAns() {
        return descAns;
    }

    public void setDescAns(String descAns) {
        this.descAns = descAns;
    }

    public String getDateAns() {
        return dateAns;
    }

    public void setDateAns(String dateAns) {
        this.dateAns = dateAns;
    }

    public String getSingleAns() {
        return singleAns;
    }

    public void setSingleAns(String singleAns) {
        this.singleAns = singleAns;
    }

    public String getMultiAns() {
        return multiAns;
    }

    public void setMultiAns(String multiAns) {
        this.multiAns = multiAns;
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
        dest.writeString(testName);
        dest.writeString(selectedQoId);
        dest.writeString(descAns);
        dest.writeString(dateAns);
        dest.writeString(singleAns);
        dest.writeString(multiAns);
    }
}
