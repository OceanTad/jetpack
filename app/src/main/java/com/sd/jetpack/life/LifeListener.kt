package com.sd.jetpack.life

abstract class LifeListener : LifeChangeListener {

    open fun onCreate(){}

    open fun onStart(){}

    open fun onResume(){}

    open fun onPause(){}

    open fun onStop(){}

    open fun onDestroy(){}

}