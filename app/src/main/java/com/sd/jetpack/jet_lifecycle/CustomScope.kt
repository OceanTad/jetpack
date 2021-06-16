package com.sd.jetpack.jet_lifecycle

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class CustomScope constructor(private val job: Job) : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    fun destroy() {
        if (job.isActive)
            job.cancel()
    }

}