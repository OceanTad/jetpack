package com.sd.jetpack.vm

import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    companion object {
        const val BASE_URL = "https://api.sunofbeach.net/shop/"
    }

    @GET(value = "onSell/{page}")
    suspend fun getOnSellList(@Path(value = "page") page: Int): ResultData<OnSellData>

}