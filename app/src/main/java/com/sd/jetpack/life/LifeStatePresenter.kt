package com.sd.jetpack.life

class LifeStatePresenter(private val lifeOwner: LifeOwner) {

    val users: DataObservable<ArrayList<UserBean>> by lazy {
        DataObservable<ArrayList<UserBean>>()
    }
    val state: DataObservable<LoadingState> by lazy {
        DataObservable<LoadingState>()
    }

    init {
        state.value = LoadingState.INIT
    }

    fun getUserData() {
        state.value = LoadingState.LOADING
        UserModel.instance.loadUserData(object : UserModel.DataCallback<ArrayList<UserBean>> {
            override fun onSuccess(data: ArrayList<UserBean>) {
                state.value = LoadingState.SUCCESS
                users.value = data
            }

            override fun onFailed(code: Int, msg: String) {
                state.value = LoadingState.FAILED
                users.value = null
            }

        }, lifeOwner)
    }

}