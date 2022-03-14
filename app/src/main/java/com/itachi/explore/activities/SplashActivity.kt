package com.itachi.explore.activities

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.itachi.explore.R
import com.itachi.explore.mvp.presenters.SplashPresenter
import com.itachi.explore.mvp.views.SplashView
import kotlinx.android.synthetic.main.dialog_loading.*

class SplashActivity : BaseActivity(),SplashView{
    override fun navigateToMain() {
        startActivity(MainActivity.newIntent(this))
        finish()
    }

    override fun navigateToLogin() {
        startActivity(LoginActivity.newIntent(this))
        finish()
    }

    override fun navigateToIntro() {
        startActivity(IntroActivity.newIntent(this))
        finish()
    }

    override fun checkLanguage(lang: String) {

    }

    lateinit var mPresenter : SplashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        mPresenter = ViewModelProviders.of(this).get(SplashPresenter::class.java)
        mPresenter.initPresenter(this)
        mPresenter.checkLanguage()
//        getKeyHashForFacebook()

        val timer = object : Thread() {
            override fun run() {
                try {
                    lottie_view.setAnimation("welcome_screen.json")
                    lottie_view.playAnimation()
                    lottie_view.loop(true)
                    sleep(5000)
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    mPresenter.checkAlreadyLogin()
                }
            }
        }
        timer.start()
    }
}