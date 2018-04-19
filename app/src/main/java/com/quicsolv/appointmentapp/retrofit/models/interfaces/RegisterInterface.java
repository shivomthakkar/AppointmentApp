package com.quicsolv.appointmentapp.retrofit.models.interfaces;

import com.quicsolv.appointmentapp.retrofit.models.pojo.ParkingListResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**********************************************************************
 * Created by   -  Tushar Patil
 * Organization -  QuicSolv Technologies Pvt.Ltd
 * Date         -  18 Apr 2018
 ***********************************************************************/

public interface RegisterInterface {
    @GET("register")
    Call<ParkingListResponse> getParkingList();
}
