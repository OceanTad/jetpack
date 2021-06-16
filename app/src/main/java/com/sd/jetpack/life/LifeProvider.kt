package com.sd.jetpack.life

class LifeProvider {

    var currentState: LifeState? = null

    private val listeners = arrayListOf<LifeListener>()

    fun dispatchState(state: LifeState){
        currentState = state
        listeners.forEach{
            it.dispatchStateChange(state)
            when(state){
                LifeState.CREATE->it.onCreate()
                LifeState.START->it.onStart()
                LifeState.RESUME->it.onResume()
                LifeState.PAUSE->it.onPause()
                LifeState.STOP->it.onStop()
                LifeState.DESTROY->it.onDestroy()
            }
        }
    }

    fun registerListener(lifeListener: LifeListener){
        if (!listeners.contains(lifeListener))
            listeners.add(lifeListener)
    }

    fun unRegisterListener(lifeListener: LifeListener){
        if (listeners.contains(lifeListener))
            listeners.remove(lifeListener)
    }

    fun unRegisterListener(){
        if (listeners.isNotEmpty())
            listeners.clear()
    }

}