package com.itachi.explore.viewholders

import android.view.View
import com.bumptech.glide.Glide
import com.itachi.core.domain.AncientVO
import com.itachi.explore.R
import kotlinx.android.synthetic.main.rv_item_ancient.view.*
import me.myatminsoe.mdetect.MDetect
import me.myatminsoe.mdetect.Rabbit

class AncientViewHolder(itemView : View, val adapterOnClick : (Int) -> Unit) : BaseViewHolder<AncientVO>(itemView){

    override fun setData(data: AncientVO, position: Int) {
        Glide.with(itemView.context.applicationContext)
            .load(data.photos?.get(0)?.url)
            .placeholder(R.drawable.ic_placeholder)
            .into(itemView.img_ancient_item)

        var title = data.title
        var about = data.about
        if(!MDetect.isUnicode()) {
            title = Rabbit.uni2zg(title)
            about = Rabbit.uni2zg(about)

        }
        itemView.tv_ancient_title.text = title
        itemView.tv_ancient_about.text = about

        itemView.setOnClickListener {
            adapterOnClick(position)
        }
    }

}