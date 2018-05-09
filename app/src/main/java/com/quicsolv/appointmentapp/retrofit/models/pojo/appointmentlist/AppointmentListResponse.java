package com.quicsolv.appointmentapp.retrofit.models.pojo.appointmentlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**********************************************************************
 * Created by   -  Tushar Patil
 * Organization -  QuicSolv Technologies Pvt.Ltd
 * Date         -  23 Apr 2018
 ***********************************************************************/

public class AppointmentListResponse {
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("ap_list")
    @Expose
    private ApList apList;
    @SerializedName("base_url")
    @Expose
    private String baseUrl;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public ApList getApList() {
        return apList;
    }

    public void setApList(ApList apList) {
        this.apList = apList;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
