package com.sd.jetpack.custom_life

class LifecycleProvider {

    var lifeState: LifeState? = null

    private val lifecycleListener = arrayListOf<LifecycleListener>()

    fun addListener(listener: LifecycleListener){
        if (!lifecycleListener.contains(listener))
            lifecycleListener.add(listener)
    }

    fun removeListener(listener: LifecycleListener){
        if (lifecycleListener.contains(listener))
            lifecycleListener.remove(listener)
    }

    fun dispatch(state: LifeState){
        this.lifeState = state
        lifecycleListener.forEach{
            when(state){
                LifeState.CREATE ->it.onCreate()
                LifeState.START -> it.onStart()
                LifeState.RESUME ->it.onResume()
                LifeState.PAUSE ->it.onPause()
                LifeState.STOP ->it.onStop()
                LifeState.DESTORY ->it.onDestroy()
            }
        }
    }

}