package com.quicsolv.appointmentapp.retrofit.models.interfaces;

import com.quicsolv.appointmentapp.retrofit.models.pojo.createappointment.CreateAppointmentResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**********************************************************************
 * Created by   -  Tushar Patil
 * Organization -  QuicSolv Technologies Pvt.Ltd
 * Date         -  18 Apr 2018
 ***********************************************************************/

public interface CreateAppointmentInterface {
    @FormUrlEncoded
    @POST("insert_appointment")
    Call<CreateAppointmentResponse> createNewAppointment(@Field("pid") String patientId,
                                             @Field("sp_id") String specialityId,
                                             @Field("description") String desc,
                                             @Field("prf_date") String prfDate,
                                             @Field("prf_time") String prfTime,
                                             @Field("imdit_apptmnt") String innidiateAppt);
}
