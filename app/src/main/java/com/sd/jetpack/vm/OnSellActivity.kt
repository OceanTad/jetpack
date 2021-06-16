package com.sd.jetpack.vm

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout
import com.sd.jetpack.R
import kotlinx.android.synthetic.main.activity_onsell.*
import kotlinx.android.synthetic.main.view_error.view.*

class OnSellActivity : AppCompatActivity() {

    private val viewModel: OnSellViewModel by lazy {
        ViewModelProvider(this).get(OnSellViewModel::class.java)
    }
    private val onSellAdapter: OnSellAdapter by lazy {
        OnSellAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onsell)
        initObserver()
        initView()
        viewModel.loadData()
    }

    private fun initView() {
        trl_refresh.apply {
            setEnableLoadmore(false)
            setEnableRefresh(true)
            setEnableOverScroll(true)
            setOnRefreshListener(object : RefreshListenerAdapter() {
                override fun onLoadMore(refreshLayout: TwinklingRefreshLayout?) {
                    super.onLoadMore(refreshLayout)
                    viewModel.loadMore()
                }

                override fun onRefresh(refreshLayout: TwinklingRefreshLayout?) {
                    super.onRefresh(refreshLayout)
                    viewModel.loadRefresh()
                }
            })

        }
        rv_list.run {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(this@OnSellActivity, LinearLayoutManager.VERTICAL, false)
            isNestedScrollingEnabled = true
            adapter = onSellAdapter.apply {
                clickListener = object : AdapterClickListener {
                    override fun onItemClick(item: OnSellData.MapData, position: Int) {
                        Toast.makeText(this@OnSellActivity, "你点击${position}了", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    private fun initObserver() {
        viewModel.run {
            contentList.observe(this@OnSellActivity, Observer {
                onSellAdapter.data = it ?: arrayListOf()
            })
            loadState.observe(this@OnSellActivity, Observer {
                trl_refresh.finishRefreshing()
                trl_refresh.finishLoadmore()
                when (it) {
                    OnSellLoadState.SUCCESS -> {
                        trl_refresh.isVisible = true
                        loading.isVisible = false
                        error.isVisible = false
                    }
                    OnSellLoadState.LOADING -> {
                        trl_refresh.isVisible = false
                        loading.isVisible = true
                        error.isVisible = false
                    }
                    OnSellLoadState.FAILED -> {
                        trl_refresh.isVisible = false
                        loading.isVisible = false
                        error.isVisible = true
                        error.tv_hint.text = "加载失败"
                    }
                    OnSellLoadState.EMPTY -> {
                        trl_refresh.isVisible = false
                        loading.isVisible = false
                        error.isVisible = true
                        error.tv_hint.text = "暂无数据"
                    }
                }
            })
            loadMoreState.observe(this@OnSellActivity, Observer {
                when (it) {
                    LoadMoreState.FINISH -> {
                        trl_refresh.finishLoadmore()
                    }
                    LoadMoreState.REFRESH_FINISH -> {
                        trl_refresh.finishRefreshing()
                    }
                    LoadMoreState.ENABLE -> {
                        trl_refresh.setEnableLoadmore(true)
                    }
                    LoadMoreState.NOT_ENABLE -> {
                        trl_refresh.setEnableLoadmore(false)
                    }
                }
            })
        }
    }

}