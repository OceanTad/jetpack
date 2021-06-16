package com.sd.jetpack.livedata_viewmodel_databing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.sd.jetpack.R
import com.sd.jetpack.databinding.ItemLvdRecyclerBinding
import com.sd.jetpack.vm.OnSellData

class LvdAdapter(private val lifecycleOwner: LifecycleOwner) :
    RecyclerView.Adapter<LvdAdapter.LvdViewHolder>() {

    var dataList: ArrayList<OnSellData.MapData> = arrayListOf()
        set(value) {
            if (field.isNotEmpty())
                field.clear()
            field.addAll(value)
            notifyDataSetChanged()
            println("**************************")
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LvdViewHolder {
        val binding: ItemLvdRecyclerBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_lvd_recycler,
            parent,
            false
        )
        binding.lifecycleOwner = lifecycleOwner
        return LvdViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: LvdViewHolder, position: Int) {
        val binding: ItemLvdRecyclerBinding? = DataBindingUtil.getBinding(holder.itemView)
        binding?.apply {
            item = dataList[position]
            executePendingBindings()
        }
    }

    inner class LvdViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}