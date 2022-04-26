package com.itachi.core.data.db

import com.itachi.core.domain.UserVO

interface UserRoomDataSource {

    suspend fun add(
        userVO: UserVO,
        onSuccess: (UserVO) -> Unit,
        onFailure: (String) -> Unit
    )

    suspend fun get(
        onSuccess: (UserVO) -> Unit,
        onFailure: (String) -> Unit
    )

    suspend fun delete()

    suspend fun update(
        userVO: UserVO,
        onSuccess: (UserVO) -> Unit,
        onFailure: (String) -> Unit
    )

}