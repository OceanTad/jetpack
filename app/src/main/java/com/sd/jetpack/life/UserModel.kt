package com.sd.jetpack.life

import kotlinx.coroutines.*

class UserModel private constructor() {

    companion object {
        val instance: UserModel by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            UserModel()
        }
    }

    fun loadUserData(callback: DataCallback<ArrayList<UserBean>>, lifeOwner: LifeOwner) {
        val job: Job = GlobalScope.launch(Dispatchers.IO) {
            delay(2000)
            if (System.currentTimeMillis() % 2 == 0L) {
                callback.onFailed(400, "请求失败")
            } else {
                val users = GlobalScope.async {
                    val result = arrayListOf<UserBean>()
                    for (i in 1..10)
                        result.add(
                            UserBean(
                                name = "lht---$i",
                                age = i + 5,
                                sex = if (i % 2 == 0) 1 else 2
                            )
                        )
                    result
                }
                callback.onSuccess(users.await())
            }
        }
        lifeOwner.getLifeProvider().registerListener(JobListener(job))
    }

    inner class JobListener(private val job:Job):LifeListener(){
        override fun dispatchStateChange(state: LifeState) {
            if (state==LifeState.DESTROY)
                job.cancel()
        }

    }

    interface DataCallback<T> {

        fun onSuccess(data: T)

        fun onFailed(code: Int, msg: String)

    }

}