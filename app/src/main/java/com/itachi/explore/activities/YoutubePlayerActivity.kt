package com.itachi.explore.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.itachi.explore.utils.YOUTUBE_API_KEY
import kotlinx.android.synthetic.main.activity_youtube_player.*
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.itachi.explore.R
import com.itachi.explore.adapters.recycler.YoutubeThumbnailRecyclerAdapter
import com.itachi.explore.mvp.presenters.YoutubePresenter
import com.itachi.explore.mvp.views.YoutubeView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_youtube_player.adView


class YoutubePlayerActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener,
    YoutubeView {

    override fun checkLanguage(lang: String) {
        when(lang) {
            "en" -> {
//                tv_views_title.text = getString(R.string.views_en)
            }

            "mm_unicode" -> {
//                tv_views_title.text = getString(R.string.views_mm_unicode)
            }

            "mm_zawgyi" -> {
//                tv_views_title.text = getString(R.string.views_mm_zawgyi)
            }
        }
    }

    override fun playClickedVideo(link: String) {
        if (mPlayer != null) {
            mPlayer.loadVideo(link)
        }
    }

    override fun showVideoList(list: ArrayList<String>) {

        if (mPlayer != null) {
            mPlayer.cueVideo(list[0])
        }
        thumbnailAdapter.setNewData(list)
    }

    private lateinit var thumbnailAdapter: YoutubeThumbnailRecyclerAdapter

    private lateinit var mPresenter: YoutubePresenter

    private lateinit var mPlayer: YouTubePlayer


    companion object {
//        const val EXTRA_EVENT_ID = "Extra_to_extra"
        const val RECOVERY_REQUEST = 1
        fun newIntent(context: Context): Intent {
            return Intent(context, YoutubePlayerActivity::class.java).apply {
            }
        }
    }

    override fun onCreate(p0: Bundle?) {
        super.onCreate(p0)
        setContentView(R.layout.activity_youtube_player)

        youtube_player.initialize(YOUTUBE_API_KEY, this)


        mPresenter = YoutubePresenter()
        mPresenter.initPresenter(this)
        mPresenter.checkLanguage()
        setUpThumbnailRecyclerView()
        showAd(adView)
        showAd(adView2)
        showAd(adView3)
        showAd(adView4)
        showAd(adView5)

    }

    override fun onStop() {
        super.onStop()
    }

    private fun setUpThumbnailRecyclerView() {
        thumbnailAdapter = YoutubeThumbnailRecyclerAdapter {
            mPresenter.onClickedItem(it)
        }
        rv_youtube_videos.adapter = thumbnailAdapter
        val layoutManager = GridLayoutManager(this, 2)
        layoutManager.reverseLayout = false
        rv_youtube_videos.layoutManager = layoutManager

    }


    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider?,
        player: YouTubePlayer,
        wasRestored: Boolean
    ) {
        mPlayer = player
        mPresenter.getYoutubeThumbnails()
        val playerStateChangeListener = MyPlayerStateChangeListener()
        player.setPlayerStateChangeListener(playerStateChangeListener)
        if (!wasRestored) {

        }

        player.setOnFullscreenListener {
            if (it) {
                player.play()
            } else player.play()
        }
    }

    override fun onInitializationFailure(
        provider: YouTubePlayer.Provider?,
        errorReason: YouTubeInitializationResult?
    ) {
        if (errorReason!!.isUserRecoverableError) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show()
        } else {
            val error = String.format(getString(R.string.player_error), errorReason.toString())
            Toast.makeText(this, error, Toast.LENGTH_LONG).show()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

    }

    private class MyPlayerStateChangeListener : YouTubePlayer.PlayerStateChangeListener {
        override fun onAdStarted() {
        }

        override fun onLoading() {
        }

        override fun onVideoStarted() {
        }

        override fun onLoaded(str: String?) {
        }

        override fun onVideoEnded() {
        }

        override fun onError(error: YouTubePlayer.ErrorReason?) {
        }

    }
    private fun showAd(adView : AdView) {

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        adView.adListener = object: AdListener() {
            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            override fun onAdFailedToLoad(adError : LoadAdError) {
                // Code to be executed when an ad request fails.
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        }
    }
}