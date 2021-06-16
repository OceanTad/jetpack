package com.sd.jetpack.custom_life

import android.os.Looper
import com.sd.jetpack.LApplication

class MusicModel<T> {

    private var blocks = arrayListOf<(T?) -> Unit>()
    private val viewLifecycleOwnerProvider = hashMapOf<(T?) -> Unit, LifecycleProvider>()

    var value: T? = null
        set(value) {
            field = value
            blocks.forEach {
                if (Looper.myLooper() == Looper.getMainLooper()) {
                    viewLifecycleOwnerProvider[it]?.lifeState
                    it.invoke(value)
                } else {
                    LApplication.handler.post {
                        it.invoke(value)
                    }
                }
            }
        }

    fun addListener(block: (T?) -> Unit, lifecycleProvider: LifecycleProvider) {
        if (!viewLifecycleOwnerProvider.containsKey(block)) {
            viewLifecycleOwnerProvider[block] = lifecycleProvider
        }
        if (!blocks.contains(block))
            blocks.add(block)
    }

    fun removeListener(block: (T?) -> Unit) {
        if (viewLifecycleOwnerProvider.containsKey(block)) {
            viewLifecycleOwnerProvider.remove(block)
        }
        if (blocks.contains(block))
            blocks.remove(block)
    }

    fun clearListener() {
        if (viewLifecycleOwnerProvider.isNotEmpty())
            viewLifecycleOwnerProvider.clear()
        if (blocks.isNotEmpty())
            blocks.clear()
    }

}