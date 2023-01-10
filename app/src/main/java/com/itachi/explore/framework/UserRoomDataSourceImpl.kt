package com.itachi.explore.framework

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.itachi.core.data.db.UserRoomDataSource
import com.itachi.core.domain.UserVO
import com.itachi.core.common.Resource
import com.itachi.explore.framework.mappers.UserMapper
import com.itachi.explore.persistence.MyDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent

class UserRoomDataSourceImpl(
    private val auth: FirebaseAuth,
    private val userMapper: UserMapper,
    private val database: MyDatabase
) : UserRoomDataSource {

    override suspend fun addUser(userVO: UserVO) {
        withContext(Dispatchers.IO) {
            database.userDao().insertUser(userMapper.voToEntity(userVO))
        }
    }

    override fun getUser(userId: String?): Flow<UserVO> =
        database.userDao().getUser(userId ?: auth.currentUser!!.uid)
            .map {
                userMapper.entityToVO(it)
            }
            .flowOn(Dispatchers.IO)

    override suspend fun deleteUser() {
        withContext(Dispatchers.IO) {
            database.userDao().deleteUser()
        }
    }

    override suspend fun updateUser(userVO: UserVO) {
        withContext(Dispatchers.IO) {
            database.userDao().insertUser(userMapper.voToEntity(userVO))
        }
    }

    private suspend fun getAllUsers(): List<UserVO> =
        withContext(Dispatchers.IO) {
            database.userDao().getAllUsers()
                .map { userMapper.entityToVO(it) }
        }
}