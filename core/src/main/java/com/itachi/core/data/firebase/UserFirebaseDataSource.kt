package com.itachi.core.data.firebase

import com.itachi.core.domain.models.UserVO
import com.itachi.core.common.Resource
import kotlinx.coroutines.flow.Flow

interface UserFirebaseDataSource {

    fun addUser(userVO: UserVO) : Flow<Resource<UserVO>>

    fun getUserById(userId : String? = null) : Flow<Resource<UserVO>>

    fun delete(userVO: UserVO) : Flow<Resource<String>>

    fun update(userVO: UserVO) : Flow<Resource<UserVO>>

    fun signOut() : Flow<Resource<String>>

}