package com.itachi.explore.mvp.presenters

import com.itachi.explore.mvvm.model.LanguageModelImpl
import com.itachi.explore.mvvm.model.YoutubeModelImpl
import com.itachi.explore.mvp.views.YoutubeView
import org.koin.core.KoinComponent
import org.koin.core.inject

class YoutubePresenter : BasePresenter<YoutubeView>(), KoinComponent {

    private val youtubeModel : YoutubeModelImpl by inject()

    private val languageModel : LanguageModelImpl by inject()

    private val videoUrlList = ArrayList<String>()

    fun getYoutubeThumbnails() {
        youtubeModel.getAncientList(
            {
                videoUrlList.clear()
                videoUrlList.addAll(it)
                mView.showVideoList(it)
            },
            {

            }
        )
    }

    fun onClickedItem(index : Int) {
        if(videoUrlList.isNotEmpty()) {
            mView.playClickedVideo(videoUrlList[index])
        }
    }

    fun checkLanguage() {
        languageModel.getLanguage {
            mView.checkLanguage(it)
        }
    }
}