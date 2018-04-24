package com.quicsolv.appointmentapp.retrofit.models.pojo.getspeciality;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**********************************************************************
 * Created by   -  Tushar Patil
 * Organization -  QuicSolv Technologies Pvt.Ltd
 * Date         -  24 Apr 2018
 ***********************************************************************/

public class GetSpecialityResponse {
    @SerializedName("aps_list")
    @Expose
    private List<ApsList> apsList = null;

    public List<ApsList> getApsList() {
        return apsList;
    }

    public void setApsList(List<ApsList> apsList) {
        this.apsList = apsList;
    }
}
