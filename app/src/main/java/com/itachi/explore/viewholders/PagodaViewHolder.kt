package com.itachi.explore.viewholders

import android.view.View
import com.bumptech.glide.Glide
import com.itachi.core.domain.models.PagodaVO
import kotlinx.android.synthetic.main.rv_item_pagoda.view.*
import me.myatminsoe.mdetect.MDetect
import me.myatminsoe.mdetect.Rabbit


class PagodaViewHolder(itemView: View, val adapterOnClick : (Int) -> Unit) : BaseViewHolder<PagodaVO>(itemView) {


    override fun setData(data: PagodaVO, position: Int) {

        var title = data.title
        if(!MDetect.isUnicode()) {
            title = Rabbit.uni2zg(title)
        }
        itemView.tv_pagoda_item.text = title
        Glide.with(itemView.context.applicationContext)
            .load(data.photos[0].url)
            .into(itemView.img_pagoda_item)

        itemView.setOnClickListener {
            adapterOnClick(position)
        }
    }

}