package com.itachi.explore.mvvm.viewmodel

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
    private val getUserUseCase : GetUserUseCase,
    private val signOut : SignOut,
    private val getLanguageUseCase: GetLanguageUseCase,
    private val setLanguageUseCase: SetLanguageUseCase,
    private val checkAppVersionUpdate: CheckAppVersionUpdate
) : ViewModel(){

    val update = MutableLiveData<Boolean>()
    val language = MutableLiveData<String>()
    val mUserVO = MutableLiveData<UserVO>()
    val errorMsg = MutableLiveData<String>()
    val userLogin = MutableLiveData<Boolean>()
    val isUploader = MutableLiveData<Boolean>()

    fun setUp() {
        viewModelScope.launch {
            getUserUseCase().collect{ resource->
                when(resource) {
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
                getLanguageUseCase().collect {
                    language.postValue(it)
                }

        }
    }

    fun setUpLanguage(lang: String) {
        setLanguageUseCase(lang)
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