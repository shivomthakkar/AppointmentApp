package com.quicsolv.appointmentapp.retrofit.models.pojo.reporttype;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**********************************************************************
 * Created by   -  Tushar Patil
 * Organization -  QuicSolv Technologies Pvt.Ltd
 * Date         -  07 May 2018
 ***********************************************************************/

public class RtTypeList {
    @SerializedName("rt_id")
    @Expose
    private String rtId;
    @SerializedName("rt_name")
    @Expose
    private String rtName;

    public RtTypeList() {
    }

    public RtTypeList(String rtId, String rtName) {
        this.rtId = rtId;
        this.rtName = rtName;
    }

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

    @Override
    public String toString() {
        return rtName;
    }
}
