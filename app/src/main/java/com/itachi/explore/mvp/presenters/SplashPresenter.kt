package com.itachi.explore.mvp.presenters

import android.content.SharedPreferences
import com.itachi.explore.mvvm.model.AuthenticationModelImpl
import com.itachi.explore.mvvm.model.LanguageModelImpl
import com.itachi.explore.mvp.views.SplashView
import com.itachi.explore.utils.DONT_SHOW_INTRO
import org.koin.core.KoinComponent
import org.koin.core.inject

class SplashPresenter : BasePresenter<SplashView>(),KoinComponent{

    private val authModel : AuthenticationModelImpl by inject()

    private val languageModel : LanguageModelImpl by inject()

    private val sharPreferences : SharedPreferences by inject()

    fun checkAlreadyLogin() {
        mView.navigateToMain()
        if(authModel.isAlreadyLogin()){
            if(sharPreferences.getBoolean(DONT_SHOW_INTRO,false)) {
                mView.navigateToMain()
            }
            else {
                mView.navigateToIntro()
            }
        }
        else {
            mView.navigateToLogin()
        }
    }

    fun checkLanguage() {
        languageModel.getLanguage {
            mView.checkLanguage(it)
        }
    }
}