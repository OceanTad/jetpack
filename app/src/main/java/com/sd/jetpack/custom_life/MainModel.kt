package com.sd.jetpack.custom_life

class MainModel<T>{

    private var blocks = arrayListOf<(T?)->Unit>()

    var value:T?=null

    set(value) {
        field = value
        blocks.forEach{it.invoke(value)}
    }

    fun registe(block:(T?)->Unit){
        if (!blocks.contains(block))
            blocks.add(block)
    }

    fun unregist(block:(T?)->Unit){
        if (blocks.contains(block))
            blocks.remove(block)
    }

}
