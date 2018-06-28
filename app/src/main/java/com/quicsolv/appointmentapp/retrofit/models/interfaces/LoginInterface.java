package com.quicsolv.appointmentapp.retrofit.models.interfaces;

import com.quicsolv.appointmentapp.retrofit.models.pojo.login.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**********************************************************************
 * Created by   -  Tushar Patil
 * Organization -  QuicSolv Technologies Pvt.Ltd
 * Date         -  18 Apr 2018
 ***********************************************************************/

public interface LoginInterface {
    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> login(@Field("ut") String userType,
                              @Field("un") String userEmail,
                              @Field("p") String password,
                              @Field("dt") String deviceToken);
}
