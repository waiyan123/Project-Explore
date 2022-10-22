package com.itachi.explore.mvvm.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itachi.core.common.Resource
import com.itachi.core.domain.UserVO
import com.itachi.core.interactors.GetUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val getUser : GetUser
) : ViewModel(){

    private val mutableUserVOitem = MutableLiveData<UserVO>()
    val userVO : LiveData<UserVO> get() = mutableUserVOitem

    init {

    }

    fun getUser(userVO : UserVO?){
        viewModelScope.launch {
            getUser(userVO?.user_id).collect {resource->
                when(resource) {
                    is Resource.Success -> {
                        resource.data?.let {
                            mutableUserVOitem.postValue(it)
                        }
                    }
                    is Resource.Error -> {

                    }
                    is Resource.Loading -> {

                    }
                }
            }
        }
    }

    fun addItem(userVO : UserVO){

    }
}