package com.sd.jetpack.jet_lifecycle

import com.sd.jetpack.life.UserBean
import kotlinx.coroutines.*

class JetDataModel {

    fun loadPageData(scope: CoroutineScope, callBack: LoadCallback<ArrayList<UserBean>>) {
        scope.launch(Dispatchers.IO) {
            delay(5000)
            if (System.currentTimeMillis() % 2 == 0L)
                withContext(Dispatchers.Main) {
                    callBack.onFailed(401, "请求数据失败")
                }
            else {
                val data = scope.async {
                    val users = arrayListOf<UserBean>()
                    for (i in 0..10)
                        users.add(
                            UserBean(
                                name = "lht_$i",
                                age = i + 10,
                                sex = if (i % 2 == 0) 1 else 2
                            )
                        )
                    users
                }
                scope.launch(Dispatchers.Main) {
                    callBack.onSuccess(data.await())
                }
            }
        }
    }

    interface LoadCallback<T> {

        fun onSuccess(data: T)

        fun onFailed(code: Int, msg: String)

    }

}
