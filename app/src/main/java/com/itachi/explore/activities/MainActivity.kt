package com.itachi.explore.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.itachi.core.domain.UserVO
import com.itachi.explore.R
import com.itachi.explore.mvvm.viewmodel.MainViewModel
import com.itachi.explore.mvvm.viewmodel.MyViewModelProviderFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_profile.*

@AndroidEntryPoint
class MainActivity : BaseActivity(),View.OnClickListener{

    private fun dismissUpdate() {
        if(alertDialog!=null) {
            alertDialog!!.dismiss()
        }
    }

    private fun forceUpdate() {
        showForceUpdateDialog()
    }

    private fun isUploader(uploader: Boolean) {

        if(uploader) {

            fab_add.visibility = View.VISIBLE
        }
        else {
            fab_add.visibility = View.GONE
        }
    }

    private fun showLanguage(lang: String) {
        when(lang) {
            "en" -> {
                toggle_btn_en.isChecked = true
                toggle_btn_mm.isChecked = false
                toggle_btn_en.isCheckable = false
                toggle_btn_mm.isCheckable = true
                tv_pagoda.text = getString(R.string.pagoda_en)
                tv_views.text = getString(R.string.views_en)
                tv_ancients.text = getString(R.string.ancients_en)
                tv_foods.text = getString(R.string.foods_en)
                tv_accessories.text = getString(R.string.accessories_en)
                tv_supports.text = getString(R.string.supports_en)
            }

            "mm_unicode" -> {
                toggle_btn_en.isChecked = false
                toggle_btn_mm.isChecked = true
                toggle_btn_en.isCheckable = true
                toggle_btn_mm.isCheckable = false
                tv_pagoda.text = getString(R.string.pagoda_mm_unicode)
                tv_views.text = getString(R.string.views_mm_unicode)
                tv_ancients.text = getString(R.string.ancients_mm_unicode)
                tv_foods.text = getString(R.string.foods_mm_unicode)
                tv_accessories.text = getString(R.string.accessories_mm_unicode)
                tv_supports.text = getString(R.string.supports_mm_unicode)
            }

            "mm_zawgyi" -> {
                toggle_btn_en.isChecked = false
                toggle_btn_mm.isChecked = true
                toggle_btn_en.isCheckable = true
                toggle_btn_mm.isCheckable = false
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
        tv_user_name.text = userVO.name
        Glide.with(applicationContext)
            .load(userVO.profile_pic!!.url)
            .placeholder(R.drawable.ic_account_circle_black_24dp)
            .into(btn_user_profile)
    }

    companion object {
//        const val EXTRA_EVENT_ID = "Extra_to_extra"
        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java).apply {
            }
        }
    }

    private val mViewModel : MainViewModel by viewModels()
    private var mInterstitialAd: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var adRequest = AdRequest.Builder().build()

        InterstitialAd.load(this,"ca-app-pub-3232751636100261/1858326901", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d("test---", adError?.message)
//                showToast(adError?.message)
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d("test---", "Ad was loaded.")
                mInterstitialAd = interstitialAd
            }
        })

        mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                Log.d("test---", "Ad was dismissed.")
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                Log.d("test---", "Ad failed to show.")
            }

            override fun onAdShowedFullScreenContent() {
                Log.d("test---", "Ad showed fullscreen content.")
                mInterstitialAd = null
            }
        }

        if (mInterstitialAd != null) {
            mInterstitialAd?.show(this)
        } else {
            Log.d("test---", "The interstitial ad wasn't ready yet.")
        }

        mViewModel.language.observe(this, Observer {
            showLanguage(it)
        })

        setUpForceUpdateDialog()
        showAd()

        card1.setOnClickListener(this)
        card2.setOnClickListener(this)
        card3.setOnClickListener(this)
        card4.setOnClickListener(this)
        card5.setOnClickListener(this)
        card6.setOnClickListener(this)

        btn_user_profile.setOnClickListener (this)

        fab_add.setOnClickListener(this)

        toggle_group_language.addOnButtonCheckedListener { group, checkedId, isChecked ->
            when(checkedId) {
                R.id.toggle_btn_en -> {
                    if(isChecked) {
                        toggle_btn_en.isCheckable = false
                        toggle_btn_mm.isCheckable = true
                        mViewModel.setLanguage("en")
                    }

                }

                R.id.toggle_btn_mm -> {
                    if(isChecked) {
                        toggle_btn_mm.isCheckable = false
                        toggle_btn_en.isCheckable = true
                        mViewModel.setLanguage("mm")
                    }

                }
            }
        }

        mViewModel.checkLanguage()
        mViewModel.onCheckUpdate()
    }

    override fun onResume() {
        super.onResume()
        mViewModel.update.observe(this) {
            if (it) {
                forceUpdate()
            } else dismissUpdate()
        }
        mViewModel.mUserVO.observe(this) {
            showUserInfo(it)
        }
        mViewModel.userLogin.observe(this) {isLogin->
            if(!isLogin) {
                logoutUser()
            }
        }
        mViewModel.errorMsg.observe(this){
            showToast(it)
        }

        mViewModel.isUploader.observe(this){
            isUploader(it)
        }

    }

    override fun onClick(p0: View?) {
        when(p0?.id) {

            R.id.card1 -> {
                startActivity(PagodasActivity.newIntent(this))
            }

            R.id.card2 -> {
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

            R.id.btn_user_profile -> {
                showProfileDialog()
            }

            R.id.fab_add -> {
                startActivity(FormActivity.newIntent(this))
            }

        }
    }

    @SuppressLint("InflateParams")
    private fun showProfileDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.dialog_profile,null)
        dialogBuilder.setView(view)
        alertDialog = dialogBuilder.create()
        alertDialog!!.window!!.attributes.windowAnimations = R.style.DialogChosing
        alertDialog!!.show()

//        alertDialog!!.tv_logout.setOnClickListener (this)
        alertDialog!!.tv_watchvideos.setOnClickListener(this)
//        alertDialog!!.tv_view_profile.setOnClickListener(this)
    }

    private fun showAd() {

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        adView.adListener = object: AdListener() {
            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
//                Log.d("test---","Ads is on loading")
            }

            override fun onAdFailedToLoad(adError : LoadAdError) {
                // Code to be executed when an ad request fails.
//                Log.d("test---","Ads is failed on loading")
//                Log.d("test---",adError.message)
//                showToast(adError.toString())
            }

            override fun onAdOpened() {
//                Log.d("test---","Ads is opening now")
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            override fun onAdClicked() {
//                Log.d("test---","Ads on clicked")
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdClosed() {
//                Log.d("test---","Ads on closed")
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        }
    }
}
