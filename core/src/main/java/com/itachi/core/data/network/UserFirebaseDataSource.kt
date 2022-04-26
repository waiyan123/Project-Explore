package com.itachi.core.data.network

import com.itachi.core.domain.AncientVO
import com.itachi.core.domain.UserVO

interface UserFirebaseDataSource {

    suspend fun addUser(
        userVO: UserVO,
        onSuccess: (UserVO) -> Unit,
        onFailure: (String) -> Unit
    )

    suspend fun getUser(
        userVO: UserVO,
        onSuccess: (UserVO) -> Unit,
        onFailure: (String) -> Unit
    )

    suspend fun getUploader(
        userId : String,
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