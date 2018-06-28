package com.quicsolv.appointmentapp.retrofit.models.pojo.questionnaries;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**********************************************************************
 * Created by   -  Tushar Patil
 * Organization -  QuicSolv Technologies Pvt.Ltd
 * Date         -  28 Jun 2018
 ***********************************************************************/

public class SubQuestion {
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("qt_id")
    @Expose
    private String qtId;
    @SerializedName("ans")
    @Expose
    private String ans;

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

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

}
