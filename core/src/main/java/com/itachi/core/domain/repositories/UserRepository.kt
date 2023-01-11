package com.itachi.core.domain.repositories

import com.itachi.core.domain.models.UserVO
import com.itachi.core.common.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun addUser(userVO : UserVO) : Flow<Resource<UserVO>>
    fun getUserById(userId : String?) : Flow<Resource<UserVO>>
    fun deleteUser(userVO: UserVO) : Flow<Resource<String>>
    fun updateUser(userVO: UserVO) : Flow<Resource<UserVO>>
    fun signOut() : Flow<Resource<String>>
}