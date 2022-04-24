package com.itachi.core.data.network

import com.itachi.core.domain.AncientVO
import com.itachi.core.domain.UserVO

interface UserFirebaseDataSource {

    suspend fun add(
        userVO: UserVO,
        onSuccess: (UserVO) -> Unit,
        onFailure: (String) -> Unit
    )

    suspend fun get(
        userVO: UserVO,
        onSuccess: (UserVO) -> Unit,
        onFailure: (String) -> Unit
    )

    suspend fun delete(
        userVO: UserVO,
        onSuccess: (UserVO) -> Unit,
        onFailure: (String) -> Unit
    )

    suspend fun update(
        userVO: UserVO,
        onSuccess: (UserVO) -> Unit,
        onFailure: (String) -> Unit
    )

}