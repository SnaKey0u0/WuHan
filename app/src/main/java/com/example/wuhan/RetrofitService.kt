package com.example.wuhan

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RetrofitService {

    @GET("/checkByTeacher")
    fun checkByTeacher(
        @Query("userId") studentId: String
    ): Call<List<SignUpRecord>>


    @POST("/signUp")
    fun signUp(
        @Body
        record: SignUpRecord
    ): Call<String>
}