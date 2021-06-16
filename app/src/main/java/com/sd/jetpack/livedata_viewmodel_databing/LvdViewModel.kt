package com.sd.jetpack.livedata_viewmodel_databing

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sd.jetpack.vm.OnSellData
import com.sd.jetpack.vm.OnSellRepository
import kotlinx.coroutines.launch

class LvdViewModel : ViewModel() {

    val loadState: ObservableField<LoadState> by lazy {
        ObservableField<LoadState>()
    }
    val loadMoreState: ObservableField<LoadMoreState> by lazy {
        ObservableField<LoadMoreState>()
    }
    var loadMoreFinished: MutableLiveData<Boolean> = MutableLiveData()
    var loadRefreshFinished: MutableLiveData<Boolean> = MutableLiveData()
    val dataList: MutableLiveData<ArrayList<OnSellData.MapData>> by lazy {
        MutableLiveData<ArrayList<OnSellData.MapData>>()
    }

    init {
        loadState.set(LoadState.LOADING)
        loadMoreState.set(LoadMoreState.ENABLE)
        loadMoreFinished.value = true
        loadRefreshFinished.value = true
    }

    private val repository: OnSellRepository by lazy {
        OnSellRepository()
    }

    private var page: Int = 1

    fun loadData() {
        loadState.set(LoadState.LOADING)
        loadRefreshFinished.value = true
        page = 1
        load(page)
    }

    fun loadMore() {
        loadRefreshFinished.value = true
        loadMoreFinished.value = false
        load(++page)
    }

    fun loadRefresh() {
        loadRefreshFinished.value = false
        page = 1
        load(page)
    }

    fun load(page: Int) {
        viewModelScope.launch {
            try {
                val result = repository.getOnSellList(page)
                if (result.apiData().response == null) {
                    loadMoreState.set(LoadMoreState.ENABLE)
                    loadState.set(LoadState.SUCCESS)
                }
                if (result.apiData().response.result.data.isNullOrEmpty()) {
                    loadState.set(LoadState.EMPTY)
                    loadMoreState.set(LoadMoreState.ENABLE)
                } else {
                    loadState.set(LoadState.SUCCESS)
                    loadMoreState.set(LoadMoreState.ABLE)
                }
                if (page != 1 && loadRefreshFinished.value ?: true) {
                    val old = dataList.value
                    old?.addAll(result.apiData().response.result.data)
                    dataList.value = old
                } else {
                    dataList.value = result.apiData().response.result.data
                }
            } catch (e: Exception) {
                if (page == 1 && loadRefreshFinished.value ?: true) {
                    dataList.value = null
                    loadState.set(LoadState.FAILED)
                } else if (loadRefreshFinished.value ?: false)
                    loadRefreshFinished.value = true
                else {
                    this@LvdViewModel.page--
                    loadMoreFinished.value = true
                }
            }
        }
    }

}