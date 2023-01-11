package com.itachi.explore.third_parties

import com.itachi.core.domain.models.PhotoVO
import ss.com.bannerslider.adapters.SliderAdapter
import ss.com.bannerslider.viewholder.ImageSlideViewHolder

class PagodaSliderAdapter(list : List<PhotoVO>) : SliderAdapter(){

    private val photoList : List<PhotoVO> = list

    override fun getItemCount(): Int {
        return photoList.size
    }

    override fun onBindImageSlide(position: Int, imageSlideViewHolder: ImageSlideViewHolder?) {

            imageSlideViewHolder?.bindImageSlide(photoList[position].url)

    }

}