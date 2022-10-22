package com.itachi.explore.mvvm.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itachi.core.domain.UserVO
import com.itachi.core.common.Resource
import com.itachi.core.interactors.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getUser : GetUser,
    private val signOut : SignOut,
    private val getLanguage: GetLanguage,
    private val setLanguage: SetLanguage,
    private val checkAppVersionUpdate: CheckAppVersionUpdate
) : ViewModel(){

    val update = MutableLiveData<Boolean>()
    val language = MutableLiveData<String>()
    val mUserVO = MutableLiveData<UserVO>()
    val errorMsg = MutableLiveData<String>()
    val userLogin = MutableLiveData<Boolean>()
    val isUploader = MutableLiveData<Boolean>()

    init {
        viewModelScope.launch {
            getUser().collect{resource->
                Log.d("test---","collect in main view model")
                when(resource) {
                    is Resource.Success -> {
                        resource.data?.let {
                            mUserVO.postValue(it)
                        }
                    }
                    is Resource.Error -> {
                        resource.message?.let {
                            errorMsg.postValue(it)
                            Log.d("test---","error $it")
                        }
                    }
                    is Resource.Loading -> {
                        errorMsg.postValue("Loading")
                        Log.d("test---","Loading")
                    }
                    else -> {

                    }
                }
            }
        }
        checkLanguage()
        checkUpdate()
    }

    fun onClickedLogout() {
        viewModelScope.launch {
            signOut().collect {
                it.data?.let {
                    userLogin.postValue(false)
                }
            }
        }

    }

    private fun checkLanguage() {
        viewModelScope.launch {
            getLanguage().collect {
                language.postValue(it)
            }
        }
    }

    fun setUpLanguage(lang: String) {
        setLanguage(lang)
        checkLanguage()
    }

    private fun checkUpdate() {
        viewModelScope.launch {
            checkAppVersionUpdate().collect {
                update.postValue(it)
            }
        }
    }
}