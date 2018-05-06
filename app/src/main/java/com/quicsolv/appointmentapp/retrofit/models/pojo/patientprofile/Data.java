package com.quicsolv.appointmentapp.retrofit.models.pojo.patientprofile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tushar on 5/1/2018.
 */

public class Data {
    @SerializedName("pid")
    @Expose
    private String pid;
    @SerializedName("p_name")
    @Expose
    private String pName;
    @SerializedName("p_email")
    @Expose
    private String pEmail;
    @SerializedName("p_pp_path")
    @Expose
    private String pPpPath;
    @SerializedName("p_phone")
    @Expose
    private String pPhone;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("q_complete")
    @Expose
    private String qComplete;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPName() {
        return pName;
    }

    public void setPName(String pName) {
        this.pName = pName;
    }

    public String getPEmail() {
        return pEmail;
    }

    public void setPEmail(String pEmail) {
        this.pEmail = pEmail;
    }

    public String getPPpPath() {
        return pPpPath;
    }

    public void setPPpPath(String pPpPath) {
        this.pPpPath = pPpPath;
    }

    public String getPPhone() {
        return pPhone;
    }

    public void setPPhone(String pPhone) {
        this.pPhone = pPhone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getQComplete() {
        return qComplete;
    }

    public void setQComplete(String qComplete) {
        this.qComplete = qComplete;
    }
}
