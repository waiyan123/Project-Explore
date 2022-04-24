package com.itachi.core.data.db

import com.itachi.core.domain.UserVO

interface UserRoomDataSource {

    suspend fun add(userVO: UserVO)
    suspend fun get() : UserVO
    suspend fun delete(userVO: UserVO)
    suspend fun update(userVO: UserVO)

}