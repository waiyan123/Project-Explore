package com.itachi.explore.viewholders

import android.os.CountDownTimer
import android.view.View
import com.bumptech.glide.Glide
import com.itachi.core.domain.ItemVO
import com.itachi.explore.R
import kotlinx.android.synthetic.main.rv_user_items.view.*
import me.myatminsoe.mdetect.MDetect
import me.myatminsoe.mdetect.Rabbit


class UserItemsViewHolder(itemView : View, val adapterOnClick : (Int) -> Unit,val editBtnOnClick : (Int) -> Unit,
val viewBtnOnClick : (Int) -> Unit) : BaseViewHolder<ItemVO>(itemView){

    override fun setData(data: ItemVO, position: Int) {
        Glide.with(itemView)
            .load(data.photos!![0].url)
            .placeholder(R.drawable.ic_placeholder)
            .into(itemView.img_user_item)
        var title = data.title
        if(!MDetect.isUnicode()) {
            title = Rabbit.uni2zg(title)
        }

        itemView.ll_items.visibility = View.VISIBLE

        itemView.tv_user_item.text = title

        itemView.setOnClickListener {
            adapterOnClick(position)

            itemView.btn_edit_item.setOnClickListener {
                editBtnOnClick(position)
            }
            itemView.btn_view_item.setOnClickListener {
                viewBtnOnClick(position)
            }
            object : CountDownTimer(3000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    itemView.ll_items.visibility = View.GONE
                    itemView.img_user_item.visibility = View.GONE
                    itemView.tv_user_item.visibility = View.GONE
                    itemView.ll_edit_item.visibility = View.VISIBLE
                    if(data.editable!!) {
                        itemView.btn_edit_item.visibility = View.VISIBLE
                    }
                    itemView.btn_view_item.visibility = View.VISIBLE

                }

                override fun onFinish() {
                    itemView.ll_items.visibility = View.VISIBLE
                    itemView.img_user_item.visibility = View.VISIBLE
                    itemView.tv_user_item.visibility = View.VISIBLE
                    itemView.ll_edit_item.visibility = View.GONE
                    itemView.btn_edit_item.visibility = View.GONE
                    itemView.btn_view_item.visibility = View.GONE

                }
            }.start()
        }
    }

}