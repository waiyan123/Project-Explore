package com.itachi.explore.third_parties

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import ss.com.bannerslider.ImageLoadingService

class GlideLoadingService(context : Context) : ImageLoadingService{

    private val mContext : Context = context

    override fun loadImage(url: String?, imageView: ImageView) {
        Glide.with(mContext)
            .load(url)
            .into(imageView)
    }

    override fun loadImage(resource: Int, imageView: ImageView) {
        Glide.with(mContext)
            .load(resource)
            .into(imageView)
    }

    override fun loadImage(
        url: String?,
        placeHolder: Int,
        errorDrawable: Int,
        imageView: ImageView
    ) {
        Glide.with(mContext)
            .load(url)
            .placeholder(placeHolder)
            .error(errorDrawable)
            .into(imageView)
    }

}