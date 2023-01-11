package com.itachi.explore.adapters.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import com.itachi.core.domain.models.PagodaVO
import com.itachi.explore.R
import com.itachi.explore.viewholders.PagodaViewHolder

class PagodasRecyclerAdapter(
    private val adapterOnClick : (Int) -> Unit) : BaseRecyclerAdapter<PagodaViewHolder , PagodaVO>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagodaViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_pagoda,parent,false)
        return PagodaViewHolder(itemView,adapterOnClick)
    }

}