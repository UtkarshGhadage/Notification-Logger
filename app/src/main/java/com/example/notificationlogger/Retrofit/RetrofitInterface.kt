package com.example.notificationlogger.Retrofit


import DataModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST



interface RetrofitInterface{
        @GET("/")
        suspend fun getData() : List<DataModel>
        @POST("/create")
        suspend fun insertData(@Body data: DataModel): Response<String>
}