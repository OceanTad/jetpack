package com.sd.jetpack.livedata_viewmodel_databing

object Util {

    @JvmStatic
    fun getHintString(loadState: LoadState): String {
        if (loadState == LoadState.FAILED)
            return "加载失败"
        else if (loadState == LoadState.EMPTY)
            return "暂无数据"
        else
            return "未知"
    }

}