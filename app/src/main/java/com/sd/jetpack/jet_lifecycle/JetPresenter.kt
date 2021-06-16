package com.sd.jetpack.jet_lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.sd.jetpack.life.UserBean
import kotlinx.coroutines.*

class JetPresenter(private val lifecycleOwner: LifecycleOwner) {

    private val model: JetDataModel by lazy {
        JetDataModel()
    }

    private val lifecycleObservable: LifecycleEventObserver by lazy {
        LifecycleEventObserver { source, event ->
            kotlin.run {
                if (event == Lifecycle.Event.ON_DESTROY) {
                    lifecycleOwner.lifecycle.removeObserver(lifecycleObservable)
                    scope.destroy()
                }
            }
        }
    }

    val state: DataLive<LoadingDataState> by lazy {
        DataLive<LoadingDataState>()
    }
    val users: DataLive<ArrayList<UserBean>> by lazy {
        DataLive<ArrayList<UserBean>>()
    }

    val liveState: MutableLiveData<LoadingDataState> by lazy {
        MutableLiveData<LoadingDataState>()
    }
    val liveUsers: MutableLiveData<ArrayList<UserBean>> by lazy {
        MutableLiveData<ArrayList<UserBean>>()
    }

    init {
        lifecycleOwner.lifecycle.addObserver(lifecycleObservable)
        state.value = LoadingDataState.INIT
        liveState.value = LoadingDataState.INIT
    }

    private val scope: CustomScope by lazy {
        CustomScope(Job())
    }

    fun getUsers() {
        state.value = LoadingDataState.LOADING
        liveState.value = LoadingDataState.LOADING
        model.loadPageData(scope,
            object : JetDataModel.LoadCallback<ArrayList<UserBean>> {
                override fun onFailed(code: Int, msg: String) {
                    state.value = LoadingDataState.FAILED
                    liveState.value = LoadingDataState.FAILED
                    users.value = null
                    liveUsers.value = null
                }

                override fun onSuccess(data: ArrayList<UserBean>) {
                    state.value = LoadingDataState.SUCCESS
                    liveState.value = LoadingDataState.SUCCESS
                    users.value = data
                    liveUsers.value = data
                }

            })
    }

}