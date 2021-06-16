package com.sd.jetpack.life

import android.os.Bundle
import com.sd.jetpack.R
import kotlinx.android.synthetic.main.activity_lifestate.*

class LifeStateActivity : BaseLifeActivity() {

    private val presenter: LifeStatePresenter by lazy {
        LifeStatePresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lifestate)
        initListener()
        initDataListener()
    }

    private fun initDataListener() {
        presenter.state.observe({
            when (it) {
                LoadingState.INIT -> tv_click.text = "加载数据"
                LoadingState.LOADING -> {
                    tv_click.text = "加载中"
                    tv_click.isEnabled = false
                }
                LoadingState.SUCCESS -> {
                    tv_click.text = "数据加载成功"
                    tv_click.isEnabled = true
                }
                LoadingState.FAILED -> {
                    tv_click.text = "数据加载失败"
                    tv_click.isEnabled = true
                }
            }
        }, this)
        presenter.users.observe({
            tv_result.text = if (it == null || it.isEmpty()) "暂无数据" else "获取数据：${it.size}"
        }, this)
    }

    private fun initListener() {
        tv_click.text = "加载数据"
        tv_click.setOnClickListener {
            presenter.getUserData()
        }
    }


}