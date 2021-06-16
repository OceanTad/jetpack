package com.sd.jetpack.custom_life

class MainPresenter private constructor(){

    enum class PlayState(val value:Int){
        NONE(0), PLAY(1), STOP(2)
    }

    var music: MainModel<MusicBean>?=
        MainModel()

    var state: MainModel<PlayState>?=
        MainModel()

    companion object{
        val instance: MainPresenter by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            MainPresenter()
        }
    }

    init {
        state?.value = PlayState.NONE
        music?.value = MusicBean(
            name = "烟花易冷",
            img = "https://upload.jianshu.io/users/upload_avatars/12438019/526aa403-cd2b-4684-814c-5a89a843ebd2?imageMogr2/auto-orient/strip|imageView2/1/w/90/h/90/format/webp",
            url = "qwer",
            id = "12345"
        )
    }

    fun doPlayOrStop(){
        if (PlayState.NONE ==state?.value) {
            music?.value = MusicBean(
                name = "烟花易冷",
                img = "https://upload.jianshu.io/users/upload_avatars/12438019/526aa403-cd2b-4684-814c-5a89a843ebd2?imageMogr2/auto-orient/strip|imageView2/1/w/90/h/90/format/webp",
                url = "qwer",
                id = "12345"
            )
            state?.value = PlayState.PLAY
        }else {
            if (state?.value == PlayState.PLAY)
                state?.value = PlayState.STOP
            else
                state?.value = PlayState.PLAY
        }
    }

    fun doPre(){
        music?.value = MusicBean(
            name = "穷开心" + System.currentTimeMillis(),
            img = "https://upload-images.jianshu.io/upload_images/6387955-c8aec63e39a9124c.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/300/h/240/format/webp",
            url = "qwer",
            id = "12345"
        )
    }

    fun doNext(){
        music?.value = MusicBean(
            name = "倍爽" + System.currentTimeMillis(),
            img = "https://upload.jianshu.io/users/upload_avatars/5396273/86ccd26d-8aaf-4011-8e06-d96f2fc4085f.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/48/h/48/format/webp",
            url = "qwer",
            id = "12345"
        )
    }

}