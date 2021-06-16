package com.sd.jetpack.jet_lifecycle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.sd.jetpack.R
import kotlinx.android.synthetic.main.activity_jet.*

class JetActivity : AppCompatActivity() {

    private val presenter: JetPresenter by lazy {
        JetPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jet)
        initViewListener()
//        initDataListener()
        initLiveDataListener()
    }

    private fun initLiveDataListener() {
        presenter.liveState.observe(this, Observer {
            when (it) {
                LoadingDataState.INIT -> tv_load.text = "加载数据"
                LoadingDataState.LOADING -> {
                    tv_load.text = "数据加载中"
                    tv_load.isEnabled = false
                }
                LoadingDataState.SUCCESS -> {
                    tv_load.text = "数据加载成功"
                    tv_load.isEnabled = true
                }
                LoadingDataState.FAILED -> {
                    tv_load.text = "数据加载失败"
                    tv_load.isEnabled = true
                }
            }
        })
        presenter.liveUsers.observe(this, Observer {
            tv_result.text = if (it == null || it.isEmpty()) "暂无数据" else "数据：${it.size}"
        })
    }

    private fun initViewListener() {
        tv_load.text = "加载数据"
        tv_load.setOnClickListener {
            presenter.getUsers()
        }
    }

    private fun initDataListener() {
        presenter.state.addListener({
            when (it) {
                LoadingDataState.INIT -> tv_load.text = "加载数据"
                LoadingDataState.LOADING -> {
                    tv_load.text = "数据加载中"
                    tv_load.isEnabled = false
                }
                LoadingDataState.SUCCESS -> {
                    tv_load.text = "数据加载成功"
                    tv_load.isEnabled = true
                }
                LoadingDataState.FAILED -> {
                    tv_load.text = "数据加载失败"
                    tv_load.isEnabled = true
                }
            }
        }, this)
        presenter.users.addListener({
            tv_result.text = if (it == null || it.isEmpty()) "暂无数据" else "数据：${it.size}"
        }, this)
    }

}