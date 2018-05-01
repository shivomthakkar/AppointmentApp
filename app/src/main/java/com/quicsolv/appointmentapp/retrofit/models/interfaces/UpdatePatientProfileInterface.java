package com.quicsolv.appointmentapp.retrofit.models.interfaces;

import com.quicsolv.appointmentapp.retrofit.models.pojo.patientprofile.UpdatePatientProfileResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**********************************************************************
 * Created by   -  Tushar Patil
 * Organization -  QuicSolv Technologies Pvt.Ltd
 * Date         -  18 Apr 2018
 ***********************************************************************/

public interface UpdatePatientProfileInterface {
    @FormUrlEncoded
    @POST("update_p_details")
    Call<UpdatePatientProfileResponse> updatePatientProfile(@Field("pid") String patientId,
                                                            @Field("un") String userName,
                                                            @Field("g") String gender,
                                                            @Field("dob") String dob);
}
