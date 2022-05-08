package com.itachi.explore.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.google.firebase.remoteconfig.BuildConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.itachi.explore.framework.Interactors
import com.itachi.explore.mvvm.model.LanguageModelImpl
import com.itachi.explore.mvvm.model.UserModelImpl
import com.itachi.explore.utils.VERSION_CODE_KEY
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.HashMap

class MainViewModel(interactors : Interactors) : AppViewmodel(interactors),KoinComponent{

    private val userModel : UserModelImpl by inject()
    private val languageModel : LanguageModelImpl by inject()
    private val remoteConfig : FirebaseRemoteConfig by inject()

    val update = MutableLiveData<Boolean>()
    val language = MutableLiveData<String>()

    init {
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

    fun onClickedLogout() {
        userModel.logOut()
    }

    fun checkLanguage(){

        languageModel.getLanguage {
            language.postValue(it)
        }

    }

    fun setLanguage(lang : String) {
        languageModel.setLanguage(lang)
        language.postValue(lang)
    }

    fun onCheckUpdate() {
        val firebaseDefaultMap = HashMap<String,Any>()
        firebaseDefaultMap.put(VERSION_CODE_KEY, BuildConfig.VERSION_CODE)
        remoteConfig.setDefaultsAsync(firebaseDefaultMap)

        remoteConfig.setConfigSettingsAsync(
            FirebaseRemoteConfigSettings.Builder()
                .build()
        )

        remoteConfig.fetch().addOnCompleteListener {
            if (it.isSuccessful()) {
                remoteConfig.activate()
                if(remoteConfig.getDouble(VERSION_CODE_KEY)> BuildConfig.VERSION_CODE) {
                    update.postValue(true)
                }
                else update.postValue(false)
            }
        }
    }
}