package com.quicsolv.appointmentapp.retrofit.models.pojo.register;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**********************************************************************
 * Created by   -  Tushar Patil
 * Organization -  QuicSolv Technologies Pvt.Ltd
 * Date         -  19 Apr 2018
 ***********************************************************************/

public class RegistrationResponse {
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("auth_token")
    @Expose
    private String authToken;
    @SerializedName("pid")
    @Expose
    private Integer pid;
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

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
