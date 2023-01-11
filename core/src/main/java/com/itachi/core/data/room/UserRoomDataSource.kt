package com.itachi.core.data.room

import com.itachi.core.domain.models.UserVO
import kotlinx.coroutines.flow.Flow

interface UserRoomDataSource {

    suspend fun addUser(userVO: UserVO)

    fun getUser(userId : String?) : Flow<UserVO>

    suspend fun deleteUser()

    suspend fun updateUser(userVO: UserVO)

}