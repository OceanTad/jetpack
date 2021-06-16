package com.sd.jetpack.custom_life

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import coil.load
import com.sd.jetpack.R
import com.sd.jetpack.jet_lifecycle.JetActivity
import com.sd.jetpack.livedata_viewmodel_databing.LvdActivity
import com.sd.jetpack.vm.OnSellActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initListener()
        initDataListener()
    }

    private fun initDataListener() {
        MainPresenter.instance.music?.registe{
            tv_title.text = it?.name
            Log.e("TAG","--url--${it?.img}")
//            Glide.with(this).load(it?.img).asBitmap().fitCenter().into(iv_img)
            iv_img.load(it?.img)
        }
        MainPresenter.instance.state?.registe {
            if (it== MainPresenter.PlayState.PLAY)
                tv_controller.text = "播放"
            else
                tv_controller.text = "暂停"
        }
    }

    private fun initListener() {
        tv_controller.setOnClickListener {
            MainPresenter.instance.doPlayOrStop()
        }
        tv_pre.setOnClickListener {
            MainPresenter.instance.doPre()
        }
        tv_next.setOnClickListener {
            MainPresenter.instance.doNext()
        }
        iv_img.setOnClickListener{
            startActivity(Intent(this, MusicActivity::class.java))
        }
        tv_title.setOnClickListener {
            startActivity(Intent(this, JetActivity::class.java))
        }
        tv_mvvm.setOnClickListener {
            startActivity(Intent(this, OnSellActivity().javaClass))
        }
        tv_databinding.setOnClickListener {
            startActivity(Intent(this, LvdActivity::class.java))
        }
    }

}
