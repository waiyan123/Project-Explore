package com.itachi.explore.adapters.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import com.itachi.core.domain.UploadedPhotoVO
import com.itachi.explore.R
import com.itachi.explore.viewholders.ViewsViewHolder

class ViewsRecyclerAdapter(val adapterOnClick : (UploadedPhotoVO) -> Unit) : BaseRecyclerAdapter<ViewsViewHolder, UploadedPhotoVO>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_views,parent,false)
        return ViewsViewHolder(view,adapterOnClick)
    }

}