package com.quicsolv.appointmentapp.retrofit.models.pojo.savesinglequesonserver;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**********************************************************************
 * Created by   -  Tushar Patil
 * Organization -  QuicSolv Technologies Pvt.Ltd
 * Date         -  27 Jun 2018
 ***********************************************************************/

public class SingleQuestionnarieResponse {
    @SerializedName("qa_id")
    @Expose
    private String qaId;
    @SerializedName("code")
    @Expose
    private Integer code;

    public String getQaId() {
        return qaId;
    }

    public void setQaId(String qaId) {
        this.qaId = qaId;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
