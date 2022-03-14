package com.itachi.explore.mvvm.model

import com.facebook.AccessToken
import com.itachi.core.domain.UserVO
import com.itachi.explore.persistence.entities.UserEntity
import io.reactivex.Observable

interface AuthenticationModel {

    fun firebaseAuthWithFacebook(
        token : AccessToken,
        onSuccess : (Observable<UserEntity>) ->Unit,
        onError : (String)->Unit)

    fun isAlreadyLogin() : Boolean
}