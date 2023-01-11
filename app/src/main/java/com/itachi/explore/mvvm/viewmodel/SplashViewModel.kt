package com.itachi.explore.mvvm.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.itachi.core.domain.usecases.GetLanguageUseCase
import com.itachi.explore.utils.DONT_SHOW_INTRO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@OptIn(InternalCoroutinesApi::class)
class SplashViewModel @Inject constructor(
    private val auth : FirebaseAuth,
    private val sharedPreferences : SharedPreferences
) : ViewModel(){

    val navigationScreenLiveData = MutableLiveData<String>()

    fun checkAlreadyLogin() {
        if(auth.currentUser!=null){
            if(sharedPreferences.getBoolean(DONT_SHOW_INTRO,false)) {
                navigationScreenLiveData.postValue("main")
            }
            else {
                navigationScreenLiveData.postValue("intro")
            }
        }
        else {
            navigationScreenLiveData.postValue("login")
        }
    }
}