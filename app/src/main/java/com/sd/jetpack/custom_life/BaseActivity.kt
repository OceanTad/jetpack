package com.sd.jetpack.custom_life

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity(), LifecycleOwner {

    override fun getLifecycleOwner(): LifecycleProvider {
        return lifecycleProvider
    }

    val lifecycleProvider: LifecycleProvider by lazy {
        LifecycleProvider()
    }

    private val lifecycleListener = arrayListOf<LifecycleListener>()

    fun addLifecyle(lifecycleListener: LifecycleListener) {
        if (!this.lifecycleListener.contains(lifecycleListener))
            this.lifecycleListener.add(lifecycleListener)
    }

    fun removeLifecycle(lifecycleListener: LifecycleListener) {
        if (this.lifecycleListener.contains(lifecycleListener))
            this.lifecycleListener.remove(lifecycleListener)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleListener.forEach {
            it.onCreate()
        }
        lifecycleProvider.dispatch(LifeState.CREATE)
    }

    override fun onStart() {
        super.onStart()
        lifecycleListener.forEach {
            it.onStart()
        }
        lifecycleProvider.dispatch(LifeState.START)
    }

    override fun onResume() {
        super.onResume()
        lifecycleListener.forEach {
            it.onResume()
        }
        lifecycleProvider.dispatch(LifeState.RESUME)
    }

    override fun onPause() {
        super.onPause()
        lifecycleListener.forEach {
            it.onPause()
        }
        lifecycleProvider.dispatch(LifeState.PAUSE)
    }

    override fun onStop() {
        super.onStop()
        lifecycleListener.forEach {
            it.onStop()
        }
        lifecycleProvider.dispatch(LifeState.STOP)
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleListener.forEach {
            it.onDestroy()
        }
        lifecycleProvider.dispatch(LifeState.DESTORY)
    }

}