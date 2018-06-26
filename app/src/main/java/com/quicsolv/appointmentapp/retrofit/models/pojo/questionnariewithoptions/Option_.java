package com.quicsolv.appointmentapp.retrofit.models.pojo.questionnariewithoptions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**********************************************************************
 * Created by   -  Tushar Patil
 * Organization -  QuicSolv Technologies Pvt.Ltd
 * Date         -  26 Jun 2018
 ***********************************************************************/

public class Option_ {
    @SerializedName("qo_id")
    @Expose
    private Object qoId;
    @SerializedName("q_option")
    @Expose
    private Object qOption;

    public Object getQoId() {
        return qoId;
    }

    public void setQoId(Object qoId) {
        this.qoId = qoId;
    }

    public Object getQOption() {
        return qOption;
    }

    public void setQOption(Object qOption) {
        this.qOption = qOption;
    }

}
