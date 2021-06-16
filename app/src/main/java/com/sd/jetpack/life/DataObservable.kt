package com.sd.jetpack.life

import android.os.Looper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DataObservable<T> {

    var value: T? = null
        set(value) {
            field = value
            observers.forEach {
                if (Looper.getMainLooper() == Looper.myLooper()) {
                    it.invoke(value)
                } else {
                    GlobalScope.launch(Dispatchers.Main) {
                        it.invoke(value)
                    }
                }
            }
        }

    private val observers = arrayListOf<(T?) -> Unit>()

    fun observe(block: (T?) -> Unit, lifeOwner: LifeOwner) {
        lifeOwner.getLifeProvider().registerListener(DataLifeListener(block))
    }

    inner class DataLifeListener(private val block: (T?) -> Unit) : LifeListener() {

        override fun dispatchStateChange(state: LifeState) {
            if (state != LifeState.DESTROY && !observers.contains(block)) {
                observers.add(block)
            } else if (state == LifeState.DESTROY && observers.contains(block)) {
                observers.remove(block)
            }
        }

    }

}