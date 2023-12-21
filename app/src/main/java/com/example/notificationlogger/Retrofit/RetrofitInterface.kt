package com.example.notificationlogger.Retrofit


import DataModel
import retrofit2.http.GET


interface RetrofitInterface {

        @GET("/")
        suspend fun getData() : List<DataModel>

}