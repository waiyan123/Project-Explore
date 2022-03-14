package com.itachi.explore.mvvm.model

import com.itachi.core.domain.UserVO
import io.reactivex.Observable

interface UserModel {

    fun getUserProfile(
        onSuccess : (UserVO) -> Unit,
        onFailure : (String?) -> Unit
    )

    fun getUserFromDb(
            onSuccess : (UserVO) -> Unit,
            onFailure: (String?) -> Unit
    )

    fun getUserById(
        userId : String,
        onSuccess : (Observable<UserVO>) -> Unit,
        onFailure: (String?) -> Unit
    )

    fun updateUserProfile(
            userVO: UserVO,
            onSuccess : (Observable<UserVO>) -> Unit,
            onFailure: (String?) -> Unit
    )

    fun logOut()
}