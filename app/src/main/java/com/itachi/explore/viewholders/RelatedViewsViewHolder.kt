package com.itachi.explore.viewholders

import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.rv_item_related_views.view.*

class RelatedViewsViewHolder (itemView: View, val adapterOnClick : (Int) -> Unit) : BaseViewHolder<String>(itemView){

    override fun setData(data: String, position: Int) {
        Glide.with(itemView.context)
            .load(data)
            .into(itemView.img_related_view)

        itemView.setOnClickListener {
            adapterOnClick(position)
        }
    }

}