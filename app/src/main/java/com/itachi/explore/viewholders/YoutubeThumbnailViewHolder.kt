package com.itachi.explore.viewholders

import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.rv_item_youtube_videos.view.*

class YoutubeThumbnailViewHolder (itemView: View, val adapterOnClick : (Int) -> Unit) : BaseViewHolder<String>(itemView){

    override fun setData(data: String, position: Int) {
//        val title = Util.getTitleQuietly(data)
//        itemView.tv_movie_title.text = title

        val thumbnail = "http://img.youtube.com/vi/$data/0.jpg"
        Glide.with(itemView.context)
            .load(thumbnail)
            .into(itemView.youtube_thumbnail_view)

        itemView.setOnClickListener {
            adapterOnClick(position)
        }
    }

}