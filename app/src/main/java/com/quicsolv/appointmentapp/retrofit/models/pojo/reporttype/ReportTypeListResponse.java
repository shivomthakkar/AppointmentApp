package com.quicsolv.appointmentapp.retrofit.models.pojo.reporttype;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by tushar on 5/6/2018.
 */

public class ReportTypeListResponse {
    @SerializedName("rt_type_list")
    @Expose
    private List<RtTypeList> rtTypeList = null;

    public List<RtTypeList> getRtTypeList() {
        return rtTypeList;
    }

    public void setRtTypeList(List<RtTypeList> rtTypeList) {
        this.rtTypeList = rtTypeList;
    }
}
