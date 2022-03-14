package com.itachi.explore.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<W>(itemView : View) : RecyclerView.ViewHolder(itemView) {

    abstract fun setData(data : W,position : Int)

}