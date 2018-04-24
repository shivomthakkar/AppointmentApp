package com.quicsolv.appointmentapp.retrofit.models.pojo.createappointment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**********************************************************************
 * Created by   -  Tushar Patil
 * Organization -  QuicSolv Technologies Pvt.Ltd
 * Date         -  24 Apr 2018
 ***********************************************************************/

public class CreateAppointmentResponse {
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("ap_id")
    @Expose
    private Integer apId;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getApId() {
        return apId;
    }

    public void setApId(Integer apId) {
        this.apId = apId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
