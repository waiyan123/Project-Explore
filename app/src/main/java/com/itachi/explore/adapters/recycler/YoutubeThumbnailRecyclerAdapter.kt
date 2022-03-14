package com.itachi.explore.adapters.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import com.itachi.explore.R
import com.itachi.explore.viewholders.YoutubeThumbnailViewHolder

class YoutubeThumbnailRecyclerAdapter(val adapterOnClick : (Int) -> Unit)
    : BaseRecyclerAdapter<YoutubeThumbnailViewHolder,String>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YoutubeThumbnailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_youtube_videos,parent,false)
        return YoutubeThumbnailViewHolder(view,adapterOnClick)
    }
}