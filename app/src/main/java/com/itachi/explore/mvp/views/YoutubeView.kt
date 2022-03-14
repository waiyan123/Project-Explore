package com.itachi.explore.mvp.views

interface YoutubeView : BaseView{

    fun showVideoList(list : ArrayList<String>)

    fun playClickedVideo(link : String)
}