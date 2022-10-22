package com.itachi.core.data.db

import com.itachi.core.domain.UserVO
import com.itachi.core.common.Resource
import kotlinx.coroutines.flow.Flow

interface UserRoomDataSource {

    suspend fun addUser(userVO: UserVO)

    fun getUser() : Flow<Resource<UserVO>>

    suspend fun deleteUser()

    suspend fun updateUser(userVO: UserVO)

}