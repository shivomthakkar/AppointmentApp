package com.quicsolv.appointmentapp.retrofit.models.interfaces;

import com.quicsolv.appointmentapp.retrofit.models.pojo.resetpassword.ResetPasswordResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**********************************************************************
 * Created by   -  Tushar Patil
 * Organization -  QuicSolv Technologies Pvt.Ltd
 * Date         -  18 Apr 2018
 ***********************************************************************/

public interface ResetPasswordInterface {
    @FormUrlEncoded
    @POST("change_password")
    Call<ResetPasswordResponse> resetPassword(@Field("pid") String patientId,
                                              @Field("op") String oldPswd,
                                              @Field("np") String newPswd);
}
