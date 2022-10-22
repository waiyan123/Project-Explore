package com.itachi.core.interactors

import com.itachi.core.data.UserDataSource
import com.itachi.core.data.UserRepository
import com.itachi.core.domain.UserVO

class AddUser(private val userDataSource: UserDataSource) {
    operator fun invoke(userVO : UserVO) = userDataSource.addUser(userVO)
}