package com.sd.jetpack.custom_life

import android.util.Log
import com.sd.jetpack.custom_life.DataModel.CallBack

class MusicPresenter(lifecycleOwner: LifecycleOwner) :
    LifecycleListener {

    private val musicViewModel: MusicViewModel by lazy {
        MusicViewModel()
    }

    init {
        lifecycleOwner.getLifecycleOwner().addListener(musicViewModel)
    }

    inner class MusicViewModel : LifecycleListener {

        override fun onCreate() {
            Log.e("TAG", "---inner onCreate---")
        }

        override fun onStart() {
            Log.e("TAG", "---inner onStart---")
        }

        override fun onResume() {
            Log.e("TAG", "---inner onResume---")
        }

        override fun onPause() {
            Log.e("TAG", "---inner onPause---")
        }

        override fun onStop() {
            Log.e("TAG", "---inner onStop---")
        }

        override fun onDestroy() {
            Log.e("TAG", "---inner onDestroy---")
        }

    }

    override fun onCreate() {
        Log.e("TAG", "---onCreate---")
    }

    override fun onStart() {
        Log.e("TAG", "---onStart---")
    }

    override fun onResume() {
        Log.e("TAG", "---onResume---")
    }

    override fun onPause() {
        Log.e("TAG", "---onPause---")
    }

    override fun onStop() {
        Log.e("TAG", "---onStop---")
    }

    override fun onDestroy() {
        Log.e("TAG", "---onDestroy---")
    }

    val model: MusicModel<ArrayList<MusicBean>> by lazy {
        MusicModel<ArrayList<MusicBean>>()
    }
    val state: MusicModel<State> by lazy {
        MusicModel<State>()
    }
    private val data: DataModel by lazy {
        DataModel()
    }

    fun loadData(page: Int, size: Int) {
        state.value = State.LOADING
        data.getLoadData(page, size, object : CallBack {
            override fun onFailed(error: String) {
                state.value = State.ERROR
                model.value?.clear()
            }

            override fun onSuccess(result: ArrayList<MusicBean>) {
                state.value = State.SUCCESS
                model.value = result
            }
        })
    }

    enum class State {
        LOADING, SUCCESS, ERROR
    }

}