package com.itachi.explore.mvp.presenters

import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.itachi.explore.mvvm.model.LanguageModelImpl
import com.itachi.explore.mvp.views.SplashView
import com.itachi.explore.utils.DONT_SHOW_INTRO
import org.koin.core.KoinComponent
import org.koin.core.inject

class SplashPresenter : BasePresenter<SplashView>(),KoinComponent{

    private val firebaseAuthRef = FirebaseAuth.getInstance()

    private val languageModel : LanguageModelImpl by inject()

    private val sharPreferences : SharedPreferences by inject()

    fun checkAlreadyLogin() {
        if(firebaseAuthRef.currentUser!=null){
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