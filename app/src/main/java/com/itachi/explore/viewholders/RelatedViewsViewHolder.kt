package com.itachi.explore.viewholders

import android.view.View
import com.bumptech.glide.Glide
import com.itachi.core.domain.PhotoVO
import com.itachi.core.domain.UploadedPhotoVO
import kotlinx.android.synthetic.main.rv_item_related_views.view.*

class RelatedViewsViewHolder (itemView: View, val adapterOnClick : (PhotoVO) -> Unit) : BaseViewHolder<PhotoVO>(itemView){

    override fun setData(data: PhotoVO, position: Int) {
        Glide.with(itemView.context)
            .load(data.url)
            .into(itemView.img_related_view)

        itemView.setOnClickListener {
            adapterOnClick(data)
        }
    }

}