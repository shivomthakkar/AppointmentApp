package com.quicsolv.appointmentapp.retrofit.models.pojo.submitQuesAns;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**********************************************************************
 * Created by   -  Tushar Patil
 * Organization -  QuicSolv Technologies Pvt.Ltd
 * Date         -  23 Apr 2018
 ***********************************************************************/

public class SubmitQuesAnsRequest {
    @SerializedName("pid")
    @Expose
    private Integer pid;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }
}
