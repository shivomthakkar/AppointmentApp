package com.quicsolv.appointmentapp.retrofit.models.pojo.uploadedfilelist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**********************************************************************
 * Created by   -  Tushar Patil
 * Organization -  QuicSolv Technologies Pvt.Ltd
 * Date         -  04 May 2018
 ***********************************************************************/

public class Datum {

    @SerializedName("lr_id")
    @Expose
    private String lrId;
    @SerializedName("pid")
    @Expose
    private String pid;
    @SerializedName("lid")
    @Expose
    private String lid;
    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("rt_id")
    @Expose
    private String rtId;
    @SerializedName("lr_path")
    @Expose
    private String lrPath;
    @SerializedName("created_datetime")
    @Expose
    private String createdDatetime;
    @SerializedName("rt_name")
    @Expose
    private String rtName;
    @SerializedName("full_name")
    @Expose
    private Object fullName;
    @SerializedName("p_name")
    @Expose
    private String pName;
    @SerializedName("l_name")
    @Expose
    private String lName;

    public String getLrId() {
        return lrId;
    }

    public void setLrId(String lrId) {
        this.lrId = lrId;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getLid() {
        return lid;
    }

    public void setLid(String lid) {
        this.lid = lid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getRtId() {
        return rtId;
    }

    public void setRtId(String rtId) {
        this.rtId = rtId;
    }

    public String getLrPath() {
        return lrPath;
    }

    public void setLrPath(String lrPath) {
        this.lrPath = lrPath;
    }

    public String getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(String createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public String getRtName() {
        return rtName;
    }

    public void setRtName(String rtName) {
        this.rtName = rtName;
    }

    public Object getFullName() {
        return fullName;
    }

    public void setFullName(Object fullName) {
        this.fullName = fullName;
    }

    public String getPName() {
        return pName;
    }

    public void setPName(String pName) {
        this.pName = pName;
    }

    public String getLName() {
        return lName;
    }

    public void setLName(String lName) {
        this.lName = lName;
    }
}
