package com.itachi.explore.viewholders

import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.itachi.core.domain.models.UploadedPhotoVO
import com.itachi.explore.R
import kotlinx.android.synthetic.main.item_views.view.*

class ViewsViewHolder(itemView : View,val adapterOnClick : (UploadedPhotoVO) -> Unit) : BaseViewHolder<UploadedPhotoVO>(itemView) {

    override fun setData(data: UploadedPhotoVO, position: Int) {

        Glide.with(itemView.context)
            .asBitmap()
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_placeholder)
            .load(data.url)
            .apply(
                RequestOptions().override(
                    itemView.img_view.width,
                    itemView.img_view.height
                )
            )
            .into(itemView.img_view)

        itemView.setOnClickListener {
            adapterOnClick(data)
        }
    }
}