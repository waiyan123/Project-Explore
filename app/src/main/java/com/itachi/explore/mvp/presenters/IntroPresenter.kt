package com.itachi.explore.mvp.presenters

import android.content.SharedPreferences
import androidx.lifecycle.Observer
import com.itachi.explore.activities.IntroActivity
import com.itachi.explore.mvvm.model.LanguageModelImpl
import com.itachi.explore.mvvm.model.ShweboModelImpl
import com.itachi.explore.mvp.views.IntroView
import com.itachi.explore.utils.DONT_SHOW_INTRO
import org.koin.core.KoinComponent
import org.koin.core.inject

class IntroPresenter : BasePresenter<IntroView>(),KoinComponent{

    private val shweboModel : ShweboModelImpl by inject()
    private val languageModel : LanguageModelImpl by inject()
    private val sharPreferences : SharedPreferences by inject()

//    init {
//        shweboModel.getIntro(
//            { intro ->
//                mView.showIntro(intro.replace("_b","\n"))
//            },
//            { error ->
//                mView.showError(error)
//            }
//
//        )
//    }

    fun setLanguage(lang : String,mContext : IntroActivity) {
        languageModel.setLanguage(lang)
            .observe(mContext, Observer {
                checkLanguage()
            })
    }

    fun onClickedContinue(boo : Boolean) {
        sharPreferences.edit().putBoolean(DONT_SHOW_INTRO,boo).apply()
        mView.navigateToMainActivity()
    }

    fun checkLanguage() {
        languageModel.getLanguage {
            mView.checkLanguage(it)
        }
    }

}