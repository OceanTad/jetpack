package com.sd.jetpack.life

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

open class BaseLifeActivity : AppCompatActivity(), LifeOwner {

    private val provider: LifeProvider by lazy {
        LifeProvider()
    }

    override fun getLifeProvider(): LifeProvider {
        return provider
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        provider.dispatchState(LifeState.CREATE)
    }

    override fun onStart() {
        super.onStart()
        provider.dispatchState(LifeState.START)
    }

    override fun onResume() {
        super.onResume()
        provider.dispatchState(LifeState.RESUME)
    }

    override fun onPause() {
        super.onPause()
        provider.dispatchState(LifeState.PAUSE)
    }

    override fun onStop() {
        super.onStop()
        provider.dispatchState(LifeState.STOP)
    }

    override fun onDestroy() {
        super.onDestroy()
        provider.dispatchState(LifeState.DESTROY)
        provider.unRegisterListener()
    }

}

