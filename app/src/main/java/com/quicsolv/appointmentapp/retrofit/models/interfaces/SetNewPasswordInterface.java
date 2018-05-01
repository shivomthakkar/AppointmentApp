package com.quicsolv.appointmentapp.retrofit.models.interfaces;

import com.quicsolv.appointmentapp.retrofit.models.pojo.login.LoginResponse;
import com.quicsolv.appointmentapp.retrofit.models.pojo.resetpassword.SetNewPasswordResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**********************************************************************
 * Created by   -  Tushar Patil
 * Organization -  QuicSolv Technologies Pvt.Ltd
 * Date         -  18 Apr 2018
 ***********************************************************************/

public interface SetNewPasswordInterface {
    @FormUrlEncoded
    @POST("save_np")
    Call<SetNewPasswordResponse> setNewPassword(@Field("pe") String patientEmailId,
                                                @Field("pwd") String newPswd,
                                                @Field("rc") String resetCode);
}
