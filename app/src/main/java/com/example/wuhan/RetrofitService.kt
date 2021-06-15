package com.example.wuhan

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RetrofitService {
    @GET("/checkByStudent")
    fun checkByStudent(
        @Query("studentId") studentId: String
    ): Call<SignUpRecord>

    @GET("/checkByTeacher")
    fun checkByTeacher(
        @Query("studentId") studentId: String
    ): Call<SignUpRecord>


    @POST("/signUp")
    fun signUp(
        @Body
        record: SignUpRecord
    ): Call<String>
}