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
    private String qOption;

    public Option_() {
    }

    public Option_(String qoId, String qOption) {
        this.qoId = qoId;
        this.qOption = qOption;
    }

    public Object getQoId() {
        return qoId;
    }

    public void setQoId(Object qoId) {
        this.qoId = qoId;
    }

    public String getQOption() {
        return qOption;
    }

    public void setQOption(String qOption) {
        this.qOption = qOption;
    }

    @Override
    public String toString() {
        return qOption;
    }

}
