package com.itachi.explore.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.itachi.core.domain.UserVO

class UserProfileViewModel : ViewModel(){

    private val mutableUserVOitem = MutableLiveData<UserVO>()
    val userVO : LiveData<UserVO> get() = mutableUserVOitem

    fun addItem(itemVO : UserVO) {
        mutableUserVOitem.value = itemVO
    }
}