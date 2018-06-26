package com.quicsolv.appointmentapp.retrofit.models.interfaces;

import com.quicsolv.appointmentapp.retrofit.models.pojo.questionnariewithoptions.GetListOfQuestionnarieWithOptions;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**********************************************************************
 * Created by   -  Tushar Patil
 * Organization -  QuicSolv Technologies Pvt.Ltd
 * Date         -  18 Apr 2018
 ***********************************************************************/

public interface QuestionnarieListWithOptionsInterface {
    @FormUrlEncoded
    @POST("questionnaire")
    Call<GetListOfQuestionnarieWithOptions> getAllQuestionsWithOptions(@Field("pid") String patientId);
}
