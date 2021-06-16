package com.sd.jetpack.livedata_viewmodel_databing

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout
import com.sd.jetpack.R
import com.sd.jetpack.databinding.ActivityLvdBinding

class LvdActivity : AppCompatActivity() {

    private val viewModel: LvdViewModel by lazy {
        ViewModelProvider(this).get(LvdViewModel::class.java)
    }
    private var dataBinding: ActivityLvdBinding? = null

    private val adapter:LvdAdapter by lazy {
        LvdAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_lvd)
        dataBinding?.viewModel = viewModel
        dataBinding?.lifecycleOwner = this

        initView()
        initObserver()

        viewModel.loadData()
    }

    private fun initObserver() {
        viewModel.apply {
            dataList.observe(this@LvdActivity, Observer {
                adapter.dataList = it?: arrayListOf()
                dataBinding?.trlRefresh?.finishRefreshing()
                dataBinding?.trlRefresh?.finishLoadmore()
            })
            loadMoreFinished.observe(this@LvdActivity, Observer {
                if (it)
                    dataBinding?.trlRefresh?.finishLoadmore()
            })
            loadRefreshFinished.observe(this@LvdActivity, Observer {
                if (it)
                    dataBinding?.trlRefresh?.finishRefreshing()
            })
        }
    }

    private fun initView() {
        dataBinding?.apply {
            trlRefresh.apply {
                setEnableLoadmore(false)
                setEnableRefresh(true)
                setEnableOverScroll(true)
                setOnRefreshListener(object : RefreshListenerAdapter() {
                    override fun onLoadMore(refreshLayout: TwinklingRefreshLayout?) {
                        super.onLoadMore(refreshLayout)
                        viewModel?.loadMore()
                    }

                    override fun onRefresh(refreshLayout: TwinklingRefreshLayout?) {
                        super.onRefresh(refreshLayout)
                        viewModel?.loadRefresh()
                    }
                })
            }
            rvList.apply {
                setHasFixedSize(true)
                layoutManager =
                    LinearLayoutManager(this@LvdActivity, LinearLayoutManager.VERTICAL, false)
                isNestedScrollingEnabled = true
                adapter = this@LvdActivity.adapter
            }
        }
    }

}