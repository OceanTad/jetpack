package com.sd.jetpack.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class OnSellViewModel : ViewModel() {

    val contentList: MutableLiveData<ArrayList<OnSellData.MapData>> by lazy {
        MutableLiveData<ArrayList<OnSellData.MapData>>()
    }
    val loadState: MutableLiveData<OnSellLoadState> by lazy {
        MutableLiveData<OnSellLoadState>()
    }
    val loadMoreState: MutableLiveData<LoadMoreState> by lazy {
        MutableLiveData<LoadMoreState>()
    }

    private var page: Int = 1
    private var isRefresh = false

    init {
        loadState.value = OnSellLoadState.LOADING
        loadMoreState.value = LoadMoreState.NOT_ENABLE
    }

    private val repository: OnSellRepository by lazy {
        OnSellRepository()
    }

    fun loadData() {
        loadState.value = OnSellLoadState.LOADING
        isRefresh = false
        page = 1
        load(page)
    }

    fun loadMore() {
        isRefresh = false
        load(++page)
    }

    fun loadRefresh() {
        isRefresh = true
        page = 1
        load(page)
    }

    fun load(page: Int) {
        viewModelScope.launch {
            try {
                val result = repository.getOnSellList(page)
                if (result.apiData().response == null) {
                    loadMoreState.value = LoadMoreState.NOT_ENABLE
                    loadState.value = OnSellLoadState.SUCCESS
                }
                if (result.apiData().response.result.data.isNullOrEmpty()) {
                    loadState.value = OnSellLoadState.EMPTY
                    loadMoreState.value = LoadMoreState.NOT_ENABLE
                } else {
                    loadState.value = OnSellLoadState.SUCCESS
                    loadMoreState.value = LoadMoreState.ENABLE
                }
                if (page != 1 && !isRefresh) {
                    val old = contentList.value
                    old?.addAll(result.apiData().response.result.data)
                    contentList.value = old
                } else {
                    contentList.value = result.apiData().response.result.data
                }
            } catch (e: Exception) {
                if (page == 1 && !isRefresh) {
                    contentList.value = null
                    loadState.value = OnSellLoadState.FAILED
                } else if (isRefresh)
                    loadMoreState.value = LoadMoreState.REFRESH_FINISH
                else {
                    this@OnSellViewModel.page--
                    loadMoreState.value = LoadMoreState.FINISH
                }
            }
        }
    }

}