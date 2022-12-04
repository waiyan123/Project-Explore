package com.itachi.explore.adapters.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import com.itachi.core.domain.AncientVO
import com.itachi.explore.R
import com.itachi.explore.viewholders.AncientViewHolder

class AncientRecyclerAdapter(private val adapterOnClick : (AncientVO) -> Unit) : BaseRecyclerAdapter<AncientViewHolder, AncientVO>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AncientViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_ancient,parent,false)
        return AncientViewHolder(itemView,adapterOnClick)
    }
}