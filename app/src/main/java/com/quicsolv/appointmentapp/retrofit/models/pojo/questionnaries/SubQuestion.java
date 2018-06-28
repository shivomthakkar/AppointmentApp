package com.quicsolv.appointmentapp.retrofit.models.pojo.questionnaries;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**********************************************************************
 * Created by   -  Tushar Patil
 * Organization -  QuicSolv Technologies Pvt.Ltd
 * Date         -  28 Jun 2018
 ***********************************************************************/

public class SubQuestion {
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
    @SerializedName("desc_ans")
    @Expose
    private Object descAns;
    @SerializedName("date_ans")
    @Expose
    private Object dateAns;
    @SerializedName("single_ans")
    @Expose
    private String singleAns;
    @SerializedName("multi_ans")
    @Expose
    private Object multiAns;
    @SerializedName("options")
    @Expose
    private List<Option_> options = null;

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

    public Object getDescAns() {
        return descAns;
    }

    public void setDescAns(Object descAns) {
        this.descAns = descAns;
    }

    public Object getDateAns() {
        return dateAns;
    }

    public void setDateAns(Object dateAns) {
        this.dateAns = dateAns;
    }

    public String getSingleAns() {
        return singleAns;
    }

    public void setSingleAns(String singleAns) {
        this.singleAns = singleAns;
    }

    public Object getMultiAns() {
        return multiAns;
    }

    public void setMultiAns(Object multiAns) {
        this.multiAns = multiAns;
    }

    public List<Option_> getOptions() {
        return options;
    }

    public void setOptions(List<Option_> options) {
        this.options = options;
    }
}
