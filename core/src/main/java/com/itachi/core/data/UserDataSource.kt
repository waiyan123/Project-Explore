package com.itachi.core.data

import com.itachi.core.domain.UserVO

interface UserDataSource {

    suspend fun add(userVO: UserVO)
    suspend fun get() : UserVO
    suspend fun delete(userVO: UserVO)
    suspend fun update(userVO: UserVO)

}