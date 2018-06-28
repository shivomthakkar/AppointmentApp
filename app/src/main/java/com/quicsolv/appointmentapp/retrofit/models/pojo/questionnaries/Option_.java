package com.quicsolv.appointmentapp.retrofit.models.pojo.questionnaries;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**********************************************************************
 * Created by   -  Tushar Patil
 * Organization -  QuicSolv Technologies Pvt.Ltd
 * Date         -  28 Jun 2018
 ***********************************************************************/

public class Option_ {
    @SerializedName("qo_id")
    @Expose
    private String qoId;
    @SerializedName("q_option")
    @Expose
    private String qOption;

    public String getQoId() {
        return qoId;
    }

    public void setQoId(String qoId) {
        this.qoId = qoId;
    }

    public String getQOption() {
        return qOption;
    }

    public void setQOption(String qOption) {
        this.qOption = qOption;
    }
}
