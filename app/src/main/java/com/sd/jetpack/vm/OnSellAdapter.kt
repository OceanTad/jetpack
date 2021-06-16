package com.sd.jetpack.vm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sd.jetpack.R
import kotlinx.android.synthetic.main.item_on_sell_recycler.view.*

class OnSellAdapter : RecyclerView.Adapter<OnSellAdapter.OnSellViewHolder>() {

    var data: ArrayList<OnSellData.MapData> = arrayListOf()
        set(value) {
            if (field.isNotEmpty())
                field.clear()
            field.addAll(value)
            notifyDataSetChanged()
        }
    var clickListener: AdapterClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnSellViewHolder {
        return OnSellViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_on_sell_recycler,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: OnSellViewHolder, position: Int) {
        holder.itemView.apply {
            setOnClickListener{
                clickListener?.onItemClick(data[position], position)
            }
            tv_title.text = data[position].title
            tv_amount.text = "${data[position].coupon_amount}"
            iv_img.load("http:${data[position].pict_url}") {
                placeholder(R.mipmap.ic_launcher)
            }
        }
    }


    inner class OnSellViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}