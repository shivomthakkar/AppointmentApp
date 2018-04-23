package com.quicsolv.appointmentapp.retrofit.models.pojo.submitQuesAns;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**********************************************************************
 * Created by   -  Tushar Patil
 * Organization -  QuicSolv Technologies Pvt.Ltd
 * Date         -  23 Apr 2018
 ***********************************************************************/

public class Datum {
    @SerializedName("qid")
    @Expose
    private Integer qid;
    @SerializedName("q_answer")
    @Expose
    private Integer qAnswer;
    @SerializedName("qr_id")
    @Expose
    private Integer qrId;
    @SerializedName("p_answer")
    @Expose
    private Integer pAnswer;

    public Integer getQid() {
        return qid;
    }

    public void setQid(Integer qid) {
        this.qid = qid;
    }

    public Integer getQAnswer() {
        return qAnswer;
    }

    public void setQAnswer(Integer qAnswer) {
        this.qAnswer = qAnswer;
    }

    public Integer getQrId() {
        return qrId;
    }

    public void setQrId(Integer qrId) {
        this.qrId = qrId;
    }

    public Integer getPAnswer() {
        return pAnswer;
    }

    public void setPAnswer(Integer pAnswer) {
        this.pAnswer = pAnswer;
    }
}
