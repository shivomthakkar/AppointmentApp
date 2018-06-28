package com.quicsolv.appointmentapp.retrofit.models.interfaces;

import com.quicsolv.appointmentapp.retrofit.models.pojo.register.RegistrationResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**********************************************************************
 * Created by   -  Tushar Patil
 * Organization -  QuicSolv Technologies Pvt.Ltd
 * Date         -  18 Apr 2018
 ***********************************************************************/

public interface RegisterInterface {
    @FormUrlEncoded
    @POST("register")
    Call<RegistrationResponse> register(@Field("un") String fullName,
                                        @Field("m") String mobileNo,
                                        @Field("ue") String userEmail,
                                        @Field("p") String password,
                                        @Field("g") Integer gender,
                                        @Field("dob") String dob,
                                        @Field("dt") String deviceToken);
}
