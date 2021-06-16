package com.sd.jetpack.custom_life

class DataModel {

    fun getLoadData(page: Int, size: Int, block: CallBack) {
        val result: ArrayList<MusicBean> = arrayListOf()
        Thread {
            Thread.sleep(1000)
            if (System.currentTimeMillis() % 2 == 0L)
                block.onFailed("--error--")
            else {
                for (i: Int in (0 until size)) {
                    result.add(
                        MusicBean(
                            "name--$i",
                            "url--$i",
                            img = "img--$i",
                            id = "id--$i"

                        )
                    )
                }
                block.onSuccess(result)
            }
        }.start()
    }

    interface CallBack {

        fun onSuccess(result: ArrayList<MusicBean>)

        fun onFailed(error: String)

    }

}

