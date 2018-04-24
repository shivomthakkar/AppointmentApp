package com.quicsolv.appointmentapp.retrofit.models.pojo.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**********************************************************************
 * Created by   -  Tushar Patil
 * Organization -  QuicSolv Technologies Pvt.Ltd
 * Date         -  19 Apr 2018
 ***********************************************************************/

public class LoginResponse {
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("auth_token")
    @Expose
    private String authToken;
    @SerializedName("pid")
    @Expose
    private String pid;
    @SerializedName("qc")
    @Expose
    private String qc;
    @SerializedName("p_name")
    @Expose
    private String pName;
    @SerializedName("p_email")
    @Expose
    private String pEmail;
    @SerializedName("pp_path")
    @Expose
    private String ppPath;
    @SerializedName("p_phone")
    @Expose
    private String pPhone;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getQc() {
        return qc;
    }

    public void setQc(String qc) {
        this.qc = qc;
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

    public String getPpPath() {
        return ppPath;
    }

    public void setPpPath(String ppPath) {
        this.ppPath = ppPath;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
