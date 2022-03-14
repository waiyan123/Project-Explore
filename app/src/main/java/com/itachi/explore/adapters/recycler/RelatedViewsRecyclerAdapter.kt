package com.itachi.explore.adapters.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import com.itachi.explore.R
import com.itachi.explore.viewholders.RelatedViewsViewHolder

class RelatedViewsRecyclerAdapter(val adapterOnClick : (Int) -> Unit) : BaseRecyclerAdapter<RelatedViewsViewHolder,String>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RelatedViewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_related_views,parent,false)
        return RelatedViewsViewHolder(view,adapterOnClick)
    }
}