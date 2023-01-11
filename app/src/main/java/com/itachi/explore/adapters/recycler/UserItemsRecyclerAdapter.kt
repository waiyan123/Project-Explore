package com.itachi.explore.adapters.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import com.itachi.core.domain.models.ItemVO
import com.itachi.explore.R
import com.itachi.explore.viewholders.UserItemsViewHolder

class UserItemsRecyclerAdapter(val adapterOnClick : (Int) -> Unit, private val editBtnOnClick : (Int) -> Unit,
                               private val viewBtnOnClick : (Int) -> Unit) : BaseRecyclerAdapter<UserItemsViewHolder, ItemVO>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserItemsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_user_items,parent,false)
        return UserItemsViewHolder(view,adapterOnClick,editBtnOnClick,viewBtnOnClick)
    }
}