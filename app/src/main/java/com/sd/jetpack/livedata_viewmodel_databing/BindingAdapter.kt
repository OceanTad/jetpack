package com.sd.jetpack.livedata_viewmodel_databing

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import com.bumptech.glide.Glide
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout

@BindingMethods(
    BindingMethod(
        type = TwinklingRefreshLayout::class,
        attribute = "app:tr_enable_loadmore",
        method = "setEnableLoadmore"
    )
)

object BindingAdapter {

    @BindingAdapter("imageUrl")
    @JvmStatic fun setImageUrl(view:ImageView, url:String?){
        Glide.with(view.context).load("http:$url").into(view)
    }

}