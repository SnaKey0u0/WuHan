package com.example.wuhan

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RetrofitService {
    @GET("/checkByStudent")
    fun checkByStudent(
        @Query("name") name: String,
        @Query("room") room: String,
    ): Call<SignUpRecord>

    @GET("/checkByTeacher")
    fun checkByTeacher(
        @Query("name") name: String,
        @Query("room") room: String,
    ): Call<SignUpRecord>

    @POST("/signUp")
    fun signUp(
        record: SignUpRecord
    ): Call<String>
}