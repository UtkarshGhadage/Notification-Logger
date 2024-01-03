package com.example.notificationlogger.Retrofit

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RetrofitBuilder {

    val baseUrl = "http://10.0.2.2:4000"

    @Provides
    @Singleton
    fun getInstance(): Retrofit {

        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory( GsonConverterFactory.create())
            .build()

//        GsonConverterFactory.create(),MoshiConverterFactory.create(moshi)
    }
}
