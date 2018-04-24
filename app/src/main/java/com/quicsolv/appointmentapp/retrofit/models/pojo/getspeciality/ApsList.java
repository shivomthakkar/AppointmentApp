package com.quicsolv.appointmentapp.retrofit.models.pojo.getspeciality;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**********************************************************************
 * Created by   -  Tushar Patil
 * Organization -  QuicSolv Technologies Pvt.Ltd
 * Date         -  24 Apr 2018
 ***********************************************************************/

public class ApsList {
    @SerializedName("sp_id")
    @Expose
    private String spId;
    @SerializedName("sp_name")
    @Expose
    private String spName;

    public ApsList() {
    }

    public ApsList(String spId, String spName) {
        this.spId = spId;
        this.spName = spName;
    }


    public String getSpId() {
        return spId;
    }

    public void setSpId(String spId) {
        this.spId = spId;
    }

    public String getSpName() {
        return spName;
    }

    public void setSpName(String spName) {
        this.spName = spName;
    }

    @Override
    public String toString() {
        return spName;
    }

}
