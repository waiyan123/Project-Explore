package com.itachi.core.data

import com.itachi.core.domain.UserVO
import com.itachi.core.common.Resource
import kotlinx.coroutines.flow.Flow

interface UserDataSource {
    fun addUser(userVO : UserVO) : Flow<Resource<UserVO>>
    fun getUserById(userId : String?) : Flow<Resource<UserVO>>
    fun deleteUser(userVO: UserVO) : Flow<Resource<String>>
    fun updateUser(userVO: UserVO) : Flow<Resource<UserVO>>
    fun signOut() : Flow<Resource<String>>
}