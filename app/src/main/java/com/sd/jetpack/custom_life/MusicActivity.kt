package com.sd.jetpack.custom_life

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.sd.jetpack.R
import com.sd.jetpack.life.LifeStateActivity
import kotlinx.android.synthetic.main.activity_music.*
import kotlinx.coroutines.*

class MusicActivity : BaseActivity() {

    private val presenter: MusicPresenter by lazy {
        MusicPresenter(this)
    }

    init {
//        addLifecyle(presenter)
        lifecycleProvider.addListener(presenter)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)
        initListener()
        initDataListener()
        getData()
        testlist()
    }

    override fun onDestroy() {
        super.onDestroy()
//        removeLifecycle(presenter)
        lifecycleProvider.removeListener(presenter)
    }

    fun getData() {
        GlobalScope.launch(Dispatchers.IO) {
            val result = GlobalScope.async {
                delay(2000)
                "data"
            }
            Log.e("TAG", "---****---${result.await()}")
            GlobalScope.launch {
                delay(2000)
                Log.e("TAG", "---**launch**---")
            }
            withContext(Dispatchers.Main) {
                Log.e("TAG", "---${Thread.currentThread().name}---")
            }
        }
    }

    fun testlist() {
        val data = listOf(
            1..3,
            4..5
        )
        println("data:${data.toString()}")
        var list = data.flatMap {
            it.map {
                "it:$it"
            }
        }
        println("list:${list.toString()}")
    }

    private fun initDataListener() {
        presenter.state.addListener({
            when (it) {
                MusicPresenter.State.LOADING -> {
                    tv_result.text = "加载中..."
                }
                MusicPresenter.State.ERROR -> {
                    tv_result.text = "加载失败"
                }
                MusicPresenter.State.SUCCESS -> {
                    tv_result.text = "加载成功"
                }
            }

        }, lifecycleProvider)
        presenter.model.addListener({
            tv_result.text = "数据：${it?.size}"
        }, lifecycleProvider)
    }

    private fun initListener() {
        tv_download.setOnClickListener {
            presenter.loadData(0, 30)
        }
        tv_jump.setOnClickListener {
            startActivity(Intent(this, LifeStateActivity::class.java))
        }
    }

}