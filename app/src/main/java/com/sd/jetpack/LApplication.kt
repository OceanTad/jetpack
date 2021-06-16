package com.sd.jetpack

import android.app.Application
import android.os.Handler
import android.os.Looper

class LApplication:Application(){

    companion object{
        val handler:Handler = Handler(Looper.getMainLooper())
    }

    override fun onCreate() {
        super.onCreate()
    }

}