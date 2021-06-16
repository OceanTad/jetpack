package com.sd.jetpack.vm

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    val okHttpClient = OkHttpClient.Builder()
        .callTimeout(5000, TimeUnit.MILLISECONDS)
        .build()

    val retrofitClient = Retrofit.Builder()
        .baseUrl(ApiService.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    val apiService = retrofitClient.create(ApiService::class.java)

}