package com.sd.jetpack.vm

class OnSellRepository {

    suspend fun getOnSellList(page: Int):ResultData<OnSellData> {
        return RetrofitClient.apiService.getOnSellList(page)
    }

}