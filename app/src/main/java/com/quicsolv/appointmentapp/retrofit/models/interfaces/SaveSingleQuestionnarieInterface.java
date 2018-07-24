package com.quicsolv.appointmentapp.retrofit.models.interfaces;

import com.quicsolv.appointmentapp.retrofit.models.pojo.savesinglequesonserver.SingleQuestionnarieResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**********************************************************************
 * Created by   -  Tushar Patil
 * Organization -  QuicSolv Technologies Pvt.Ltd
 * Date         -  18 Apr 2018
 ***********************************************************************/

public interface SaveSingleQuestionnarieInterface {
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("insert_ans")
    Call<SingleQuestionnarieResponse> saveSingleQuesOnServer(@Field("pid") String patientId,
                                                             @Field("m_data[q_id]") String m_data_q_id,
                                                             @Field("m_data[qt_id]") String m_data_qt_id,
                                                             @Field("m_data[ans]") String m_data_ans,
                                                             @Field("m_data[qa_id]") String m_data_qa_id,
                                                             @Field("q_complete") Integer questionComplete);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("insert_ans")
    Call<SingleQuestionnarieResponse> saveMultiLevelQuesOnServer(@Field("pid") String patientId,
                                                                 @Field("m_data[q_id]") String m_data_q_id,
                                                                 @Field("m_data[qt_id]") String m_data_qt_id,
                                                                 @Field("m_data[ans]") String m_data_ans,
                                                                 @Field("m_data[qa_id]") String m_data_qa_id,
                                                                 @Field("s_data[q_id]") String s_data_q_id,
                                                                 @Field("s_data[qt_id]") String s_data_qt_id,
                                                                 @Field("s_data[ans]") String s_data_ans,
                                                                 @Field("s_data[qa_id]") String s_data_qa_id,
                                                                 @Field("q_complete") Integer questionComplete);
}
