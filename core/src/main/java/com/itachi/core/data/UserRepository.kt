package com.itachi.core.data

import com.itachi.core.domain.UserVO

class UserRepository(private val userDataSource: UserDataSource) {

    suspend fun addUser(userVO: UserVO) = userDataSource.add(userVO)

    suspend fun getUser() = userDataSource.get()

    suspend fun deleteUser(userVO: UserVO) = userDataSource.delete(userVO)

    suspend fun updateUser(userVO: UserVO) = userDataSource.update(userVO)

}