package com.itachi.explore.activities

import android.os.Bundle
import androidx.activity.viewModels
import com.itachi.explore.R
import com.itachi.explore.mvvm.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dialog_loading.*

@AndroidEntryPoint
class SplashActivity : BaseActivity(){
    private fun navigateToMain() {
        startActivity(MainActivity.newIntent(this))
        finish()
    }

    private fun navigateToLogin() {
        startActivity(LoginActivity.newIntent(this))
        finish()
    }

    private fun navigateToIntro() {
        startActivity(IntroActivity.newIntent(this))
        finish()
    }

    private val mViewModel : SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        mViewModel.navigationScreenLiveData.observe(this){screenDestination->
            when(screenDestination){
                "main" -> {
                    navigateToMain()
                }
                "intro" -> {
                    navigateToIntro()
                }
                "login" -> {
                    navigateToLogin()
                }
            }
        }
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
                    mViewModel.checkAlreadyLogin()
                }
            }
        }
        timer.start()
    }
}