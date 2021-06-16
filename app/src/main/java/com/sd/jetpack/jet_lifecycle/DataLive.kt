package com.sd.jetpack.jet_lifecycle

import android.os.Looper
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DataLive<T> {

    private val listeners = arrayListOf<(T?) -> Unit>()

    var value: T? = null
        set(value) {
            field = value
            listeners.forEach {
                if (Looper.getMainLooper() == Looper.myLooper())
                    it.invoke(value)
                else
                    GlobalScope.launch(Dispatchers.Main) {
                        it.invoke(value)
                    }
            }
        }

    fun addListener(block: (T?) -> Unit, lifecycleOwner: LifecycleOwner) {
        lifecycleOwner.lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                Log.e("TAG", "---state---$event")
                if (event == Lifecycle.Event.ON_DESTROY && listeners.contains(block))
                    listeners.remove(block)
                if (event == Lifecycle.Event.ON_DESTROY)
                    lifecycleOwner.lifecycle.removeObserver(this)
            }
        })
        if (!listeners.contains(block))
            listeners.add(block)
    }

    fun removeListener(block: (T?) -> Unit) {
        if (listeners.contains(block))
            listeners.remove(block)
    }

    fun clearListener() {
        if (listeners.isNotEmpty())
            listeners.clear()
    }


}