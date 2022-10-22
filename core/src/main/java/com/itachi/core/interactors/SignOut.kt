package com.itachi.core.interactors

import com.itachi.core.data.UserDataSource

class SignOut(private val userDataSource: UserDataSource) {
    operator fun invoke() = userDataSource.signOut()
}