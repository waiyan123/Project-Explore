package com.itachi.explore.mvvm.viewmodel

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.itachi.core.data.UserRepository
import com.itachi.core.domain.UserVO
import com.itachi.explore.BuildConfig
import com.itachi.explore.utils.LANGUAGE
import com.itachi.explore.utils.VERSION_CODE_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import java.util.HashMap
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val sharedPreferences: SharedPreferences,
    private val auth: FirebaseAuth,
    private val remoteConfig: FirebaseRemoteConfig
) : ViewModel(), KoinComponent {

    val update = MutableLiveData<Boolean>()
    val language = MutableLiveData<String>()
    val mUserVO = MutableLiveData<UserVO>()
    val errorMsg = MutableLiveData<String>()
    val userLogin = MutableLiveData<Boolean>()
    val isUploader = MutableLiveData<Boolean>()

    init {
        GlobalScope.launch {
            userRepository.getUserFromRoom(
                {
                    mUserVO.postValue(it)
                },
                {
                    errorMsg.postValue(it)
                }
            )
            userRepository.getUserFromFirebase(
                {
                    mUserVO.postValue(it)
                    isUploader.postValue(it.get_is_uploader)
                    GlobalScope.launch {
                        userRepository.addUserToRoom(it,
                            {

                            },
                            {

                            }
                        )
                    }
                },
                {
                    errorMsg.postValue(it)
                }
            )
        }
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
        GlobalScope.launch {
            userRepository.deleteUserFromRoom()
        }
        auth.signOut()
        userLogin.postValue(false)
    }

    fun checkLanguage() {
        language.postValue(sharedPreferences.getString(LANGUAGE, "en"))
    }

    fun setLanguage(lang: String) {
        sharedPreferences.edit().putString(LANGUAGE, lang)
        language.postValue(lang)
    }

    fun onCheckUpdate() {
        val firebaseDefaultMap = HashMap<String, Any>()
        firebaseDefaultMap.put(VERSION_CODE_KEY, BuildConfig.VERSION_CODE)
        remoteConfig.setDefaultsAsync(firebaseDefaultMap)

        remoteConfig.setConfigSettingsAsync(
            FirebaseRemoteConfigSettings.Builder()
                .build()
        )

        remoteConfig.fetch().addOnCompleteListener {
            if (it.isSuccessful()) {
                remoteConfig.activate()
                Log.d("test---","remote config "+remoteConfig.getDouble(VERSION_CODE_KEY))
                Log.d("test---","App version code "+ BuildConfig.VERSION_CODE)
                if (remoteConfig.getDouble(VERSION_CODE_KEY) > BuildConfig.VERSION_CODE) {
                    update.postValue(true)
                } else update.postValue(false)
            }
        }
    }
}