package com.itachi.explore.adapters.recycler

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.itachi.explore.viewholders.BaseViewHolder

abstract class BaseRecyclerAdapter<VH : BaseViewHolder<W>,W> : RecyclerView.Adapter<VH>(){

    private var dataList = ArrayList<W>()

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.setData(dataList[position],position)
    }

    fun setNewData(data : ArrayList<W>) {
        dataList.clear()
        dataList.addAll(data)
        notifyDataSetChanged()
    }

    fun appendNewData(data : ArrayList<W>) {
        dataList.addAll(data)
        notifyDataSetChanged()
    }

    fun clearAllData() {
        dataList.clear()
        notifyDataSetChanged()
    }
}