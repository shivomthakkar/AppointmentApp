package com.quicsolv.appointmentapp.retrofit.models.pojo.questionnaries;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**********************************************************************
 * Created by   -  Tushar Patil
 * Organization -  QuicSolv Technologies Pvt.Ltd
 * Date         -  19 Apr 2018
 ***********************************************************************/

public class Datum {
    @Expose
    private String qid;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("option1")
    @Expose
    private String option1;
    @SerializedName("option2")
    @Expose
    private String option2;
    @SerializedName("option3")
    @Expose
    private String option3;
    @SerializedName("option4")
    @Expose
    private String option4;
    @SerializedName("q_answer")
    @Expose
    private String qAnswer;
    @SerializedName("qr_id")
    @Expose
    private Object qrId;
    @SerializedName("pid")
    @Expose
    private Object pid;
    @SerializedName("p_answer")
    @Expose
    private Object pAnswer;

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public String getQAnswer() {
        return qAnswer;
    }

    public void setQAnswer(String qAnswer) {
        this.qAnswer = qAnswer;
    }

    public Object getQrId() {
        return qrId;
    }

    public void setQrId(Object qrId) {
        this.qrId = qrId;
    }

    public Object getPid() {
        return pid;
    }

    public void setPid(Object pid) {
        this.pid = pid;
    }

    public Object getPAnswer() {
        return pAnswer;
    }

    public void setPAnswer(Object pAnswer) {
        this.pAnswer = pAnswer;
    }

}
