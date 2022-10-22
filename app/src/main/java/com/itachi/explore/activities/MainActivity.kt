package com.itachi.explore.activities

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.gms.ads.*
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.itachi.core.domain.UserVO
import com.itachi.explore.R
import com.itachi.explore.mvvm.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_profile.*


@AndroidEntryPoint
class MainActivity : BaseActivity(), View.OnClickListener{

    private fun dismissUpdate() {
        if (alertDialog != null) {
            alertDialog!!.dismiss()
        }
    }

    private fun forceUpdate() {
        showForceUpdateDialog()
    }

    private fun isUploader(uploader: Boolean) {

        if (uploader) {

            fab_add.visibility = View.VISIBLE
        } else {
            fab_add.visibility = View.GONE
        }
    }

    private fun showLanguage(lang: String) {
        when (lang) {
            "en" -> {
                tv_pagoda.text = getString(R.string.pagoda_en)
                tv_views.text = getString(R.string.views_en)
                tv_ancients.text = getString(R.string.ancients_en)
                tv_foods.text = getString(R.string.foods_en)
                tv_accessories.text = getString(R.string.accessories_en)
                tv_supports.text = getString(R.string.supports_en)
            }

            "mm_unicode" -> {
                tv_pagoda.text = getString(R.string.pagoda_mm_unicode)
                tv_views.text = getString(R.string.views_mm_unicode)
                tv_ancients.text = getString(R.string.ancients_mm_unicode)
                tv_foods.text = getString(R.string.foods_mm_unicode)
                tv_accessories.text = getString(R.string.accessories_mm_unicode)
                tv_supports.text = getString(R.string.supports_mm_unicode)
            }

            "mm_zawgyi" -> {
                tv_pagoda.text = getString(R.string.pagoda_mm_zawgyi)
                tv_views.text = getString(R.string.views_mm_zawgyi)
                tv_ancients.text = getString(R.string.ancients_mm_zawgyi)
                tv_foods.text = getString(R.string.foods_mm_zawgyi)
                tv_accessories.text = getString(R.string.accessories_mm_zawgyi)
                tv_supports.text = getString(R.string.supports_mm_zawgyi)
            }
        }
    }

    private fun logoutUser() {
        startActivity(LoginActivity.newIntent(this))
        hideLoading()
        finish()
    }

    private fun showUserInfo(userVO: UserVO) {
        Log.d("test---","show user ${userVO.name}")
        tv_user_name.text = userVO.name
        Glide.with(applicationContext)
            .load(userVO.profile_pic!!.url)
            .placeholder(R.drawable.ic_account_circle_black_24dp)
            .into(img_user_profile_main)
    }

    companion object {
        //        const val EXTRA_EVENT_ID = "Extra_to_extra"
        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java).apply {
            }
        }
    }

    private val mViewModel: MainViewModel by viewModels()
    private var mInterstitialAd: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mViewModel.language.observe(this, Observer {
            showLanguage(it)
        })

        setUpForceUpdateDialog()
        loadAd()
        showAd()

        setUpClickListener()

    }

    private fun setUpClickListener() {
        card_pagoda.setOnClickListener(this)
        card_view.setOnClickListener(this)
        card3.setOnClickListener(this)
        card4.setOnClickListener(this)
        card5.setOnClickListener(this)
        card6.setOnClickListener(this)

        img_user_profile_main.setOnClickListener(this)

        fab_add.setOnClickListener(this)
        fab_language.setOnClickListener(this)
        menu_item_english.setOnClickListener(this)
        menu_item_myanmar.setOnClickListener(this)
        mViewModel.update.observe(this) {
            if (it) {
                forceUpdate()
            }
            else dismissUpdate()
        }
        mViewModel.mUserVO.observe(this) {
            showUserInfo(it)
        }
        mViewModel.userLogin.observe(this) { isLogin ->
            if (!isLogin) {
                logoutUser()
            }
        }
        mViewModel.errorMsg.observe(this) {
            showToast(it)
        }

        mViewModel.isUploader.observe(this) {
            isUploader(it)
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {

            R.id.card_pagoda -> {
                startActivity(PagodasActivity.newIntent(this))
            }

            R.id.card_view -> {
                startActivity(ViewsActivity.newIntent(this))
            }

            R.id.card3 -> {
                startActivity(AncientActivity.newIntent(this))
            }

            R.id.card4 -> {
                showToast("Coming soon")
            }

            R.id.card5 -> {
                showToast("Coming soon")
            }

            R.id.card6 -> {
                showToast("Coming soon")
            }

            R.id.tv_logout -> {
                alertDialog!!.dismiss()
                showLoading()
                val lp = WindowManager.LayoutParams()

                lp.copyFrom(alertDialog!!.window!!.attributes)
                lp.width = 350
                lp.height = 300
                alertDialog!!.window!!.attributes = lp
                mViewModel.onClickedLogout()

            }

            R.id.tv_view_profile -> {
                alertDialog!!.dismiss()
                startActivity(UserProfileActivity.newIntent(this))
            }

            R.id.tv_watchvideos -> {
                alertDialog!!.dismiss()
                startActivity(YoutubePlayerActivity.newIntent(this))
            }

            R.id.img_user_profile_main -> {
                showProfileDialog()
            }

            R.id.fab_add -> {
                startActivity(FormActivity.newIntent(this))
            }

            R.id.fab_language -> {
                chooseLanguage()
            }
            R.id.menu_item_english -> {
                showToast("English")
                layout_language_chooser.visibility = View.GONE
                mViewModel.setUpLanguage("en")
            }
            R.id.menu_item_myanmar -> {
                showToast("Myanmar")
                layout_language_chooser.visibility = View.GONE
                mViewModel.setUpLanguage("mm")
            }
        }
    }

    private fun chooseLanguage() {
        when(layout_language_chooser.visibility) {
            View.GONE -> {
                layout_language_chooser.visibility = View.VISIBLE
            }
            View.VISIBLE -> {
                layout_language_chooser.visibility = View.GONE
            }
        }
    }

    @SuppressLint("InflateParams")
    private fun showProfileDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.dialog_profile, null)
        dialogBuilder.setView(view)
        alertDialog = dialogBuilder.create()
        alertDialog!!.window!!.attributes.windowAnimations = R.style.DialogChosing
        alertDialog!!.show()

        alertDialog!!.tv_logout.setOnClickListener (this)
        alertDialog!!.tv_watchvideos.setOnClickListener(this)
        alertDialog!!.tv_view_profile.setOnClickListener(this)
    }

    private fun loadAd() {
        var adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            this,
            "ca-app-pub-3232751636100261/1858326901",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                }
            })

        mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {

            }

            override fun onAdShowedFullScreenContent() {
                mInterstitialAd = null
            }
        }

        if (mInterstitialAd != null) {
            mInterstitialAd?.show(this)
        } else {

        }
    }

    private fun showAd() {

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        adView.adListener = object : AdListener() {
            override fun onAdLoaded() {

            }

            override fun onAdFailedToLoad(adError: LoadAdError) {

            }

            override fun onAdOpened() {

            }

            override fun onAdClicked() {

            }

            override fun onAdClosed() {

            }
        }
    }
}
