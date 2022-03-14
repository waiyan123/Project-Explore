package com.itachi.explore.mvp.presenters

import androidx.lifecycle.Observer
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.itachi.explore.activities.MainActivity
import com.itachi.explore.mvvm.model.LanguageModelImpl
import com.itachi.explore.mvvm.model.UserModelImpl
import com.itachi.explore.mvp.views.MainView
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.*
import com.itachi.explore.BuildConfig
import com.itachi.explore.utils.VERSION_CODE_KEY
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings


class MainPresenter : BasePresenter<MainView>(),KoinComponent{

    private val userModel : UserModelImpl by inject()
    private val languageModel : LanguageModelImpl by inject()
    private val remoteConfig : FirebaseRemoteConfig by inject()

    fun getUserInfo() {
//        userModel.getUserProfile(
//            {
//                mView.showUserInfo(it)
//                mView.isUploader(it.get_is_uploader!!)
//            },
//            {
//                mView.failedUserInfo(it)
//            }
//        )
    }

    fun logout(){
        userModel.logOut()
        mView.isUserLogout()
    }

    fun checkLanguage(){

        languageModel.getLanguage {
           mView.checkLanguage(it)
        }

    }

    fun setLanguage(lang : String,mContext : MainActivity) {
        languageModel.setLanguage(lang)
            .observe(mContext, Observer {
                checkLanguage()
            })
    }

    fun onCheckUpdate() {
        val firebaseDefaultMap = HashMap<String,Any>()
        firebaseDefaultMap.put(VERSION_CODE_KEY, BuildConfig.VERSION_CODE)
        remoteConfig.setDefaults(firebaseDefaultMap)

        remoteConfig.setConfigSettings(
            FirebaseRemoteConfigSettings.Builder().setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build()
        )

        remoteConfig.fetch().addOnCompleteListener {
            if (it.isSuccessful()) {
                remoteConfig.activateFetched()
                if(remoteConfig.getDouble(VERSION_CODE_KEY)>BuildConfig.VERSION_CODE) {
                    mView.forceUpdate()
                }
                else mView.dismissUpdate()
            }
        }
        }

}