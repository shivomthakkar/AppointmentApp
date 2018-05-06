package com.quicsolv.appointmentapp.retrofit.models.pojo.reporttype;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tushar on 5/6/2018.
 */

public class List {
    @SerializedName("rt_id")
    @Expose
    private String rtId;
    @SerializedName("rt_name")
    @Expose
    private String rtName;

    public String getRtId() {
        return rtId;
    }

    public void setRtId(String rtId) {
        this.rtId = rtId;
    }

    public String getRtName() {
        return rtName;
    }

    public void setRtName(String rtName) {
        this.rtName = rtName;
    }
}
