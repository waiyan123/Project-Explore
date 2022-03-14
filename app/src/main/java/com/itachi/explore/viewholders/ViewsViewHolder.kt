package com.itachi.explore.viewholders

import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.itachi.core.domain.UploadedPhotoVO
import com.itachi.explore.R
import kotlinx.android.synthetic.main.item_views.view.*

class ViewsViewHolder(itemView : View,val adapterOnClick : (Int) -> Unit) : BaseViewHolder<UploadedPhotoVO>(itemView) {

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
//            .into(object : CustomTarget<Bitmap>() {
//                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//
//                    val set = ConstraintSet()
//
//                    val ratio2 =String.format("%d:%d", resource.width, resource.height)
//                    set.clone(itemView.mConstraintLayout)
//                    set.setDimensionRatio(itemView.img_view.id, ratio2)
//                    set.applyTo(itemView.mConstraintLayout)
//                    itemView.img_view.setImageBitmap(resource)
//
//                }
//
//                override fun onLoadCleared(placeholder: Drawable?) {
//
//                }
//            })

        itemView.setOnClickListener {
            adapterOnClick(position)
        }
    }
}