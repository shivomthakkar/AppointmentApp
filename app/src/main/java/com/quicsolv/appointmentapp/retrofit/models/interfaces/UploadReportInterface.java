package com.quicsolv.appointmentapp.retrofit.models.interfaces;

import com.quicsolv.appointmentapp.retrofit.models.pojo.uploadreport.UploadReportResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**********************************************************************
 * Created by   -  Tushar Patil
 * Organization -  QuicSolv Technologies Pvt.Ltd
 * Date         -  18 Apr 2018
 ***********************************************************************/

public interface UploadReportInterface {
    @Multipart
    @FormUrlEncoded
    @POST("upload_report")
    Call<UploadReportResponse> uploadReportFile(@Part MultipartBody.Part file,
                                                @Part("file") RequestBody name,
                                                @Field("pid") String patientId,
                                                @Field("rt_id ") String reportId);
}
