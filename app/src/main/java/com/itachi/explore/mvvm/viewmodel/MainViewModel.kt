package com.itachi.explore.mvvm.viewmodel

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.itachi.core.domain.UserVO
import com.itachi.core.common.Resource
import com.itachi.core.interactors.*
import com.itachi.explore.BuildConfig
import com.itachi.explore.utils.IMMEDIATE_UPDATE_REQUEST_CODE
import com.itachi.explore.utils.VERSION_CODE_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(InternalCoroutinesApi::class)
@HiltViewModel
class MainViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val signOut: SignOut,
    private val getLanguageUseCase: GetLanguageUseCase,
    private val setLanguageUseCase: SetLanguageUseCase,
    private val appUpdateManager: AppUpdateManager
) : ViewModel() {

    val update = MutableLiveData<Boolean>()
    val language = MutableLiveData<String>()
    val mUserVO = MutableLiveData<UserVO>()
    val errorMsg = MutableLiveData<String>()
    val userLogin = MutableLiveData<Boolean>()
    val isUploader = MutableLiveData<Boolean>()

    val downloadStatus = MutableLiveData<String>()

    fun setUp() {
        viewModelScope.launch {
            getUserUseCase()
                .collect(FlowCollector { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            resource.data?.let {
                                mUserVO.postValue(it)
                            }
                        }
                        is Resource.Error -> {
                            resource.message?.let {

                            }
                        }
                        is Resource.Loading -> {

                        }
                    }

                })
        }
        checkLanguage()
    }

    fun onClickedLogout() {
        viewModelScope.launch {
            signOut()
                .buffer()
                .collect(FlowCollector {
                    it.data?.let {
                        userLogin.postValue(false)
                    }
                })
        }

    }

    private fun checkLanguage() {
        viewModelScope.launch {
            getLanguageUseCase()
                .buffer()
                .collect(FlowCollector {
                    Log.d("test---", "$it")
                    language.postValue(it)
                })

        }
    }

    fun setUpLanguage(lang: String) {
        setLanguageUseCase(lang)
        checkLanguage()
    }

    fun checkForInAppUpdate(context: Context) {
        appUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE &&
                appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)
            ) {
                errorMsg.postValue(BuildConfig.VERSION_CODE.toString())

                Log.d("test---", "update available")

                appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    context as Activity,
                    AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE)
                        .setAllowAssetPackDeletion(true)
                        .build(),
                    IMMEDIATE_UPDATE_REQUEST_CODE
                )

            }
        }
    }

    fun onResume(context: Context) {
        appUpdateManager
            .appUpdateInfo
            .addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.updateAvailability()
                    == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
                ) {
                    // If an in-app update is already running, resume the update.
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        context as Activity,
                        AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE)
                            .setAllowAssetPackDeletion(true)
                            .build(),
                        IMMEDIATE_UPDATE_REQUEST_CODE
                    )
                }
            }
    }

    fun registerUpdateListener() {
        listener?.let { appUpdateManager.registerListener(it) }
    }

    fun unregisterUpdateListener() {
        listener?.let { appUpdateManager.unregisterListener(it) }
    }

    private val listener: InstallStateUpdatedListener? =
        InstallStateUpdatedListener { installState ->

            if (installState.installStatus() == InstallStatus.DOWNLOADING) {
                val bytesDownloaded = installState.bytesDownloaded()
                val totalBytesToDownload = installState.totalBytesToDownload()
                downloadStatus.postValue("Downloading latest version")
            } else if (installState.installStatus() == InstallStatus.DOWNLOADED) {
                downloadStatus.postValue("Download is completed")
                appUpdateManager.completeUpdate()
            }
        }
}