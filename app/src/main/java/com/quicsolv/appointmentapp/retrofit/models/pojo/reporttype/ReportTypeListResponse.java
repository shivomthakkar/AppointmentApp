package com.quicsolv.appointmentapp.retrofit.models.pojo.reporttype;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tushar on 5/6/2018.
 */

public class ReportTypeListResponse {
    @SerializedName("list")
    @Expose
    private java.util.List<List> list = null;

    public java.util.List<List> getList() {
        return list;
    }

    public void setList(java.util.List<List> list) {
        this.list = list;
    }
}
