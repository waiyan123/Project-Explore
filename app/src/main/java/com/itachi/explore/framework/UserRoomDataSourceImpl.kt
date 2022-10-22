package com.itachi.explore.framework

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.itachi.core.data.db.UserRoomDataSource
import com.itachi.core.domain.UserVO
import com.itachi.core.common.Resource
import com.itachi.explore.framework.mappers.UserMapper
import com.itachi.explore.persistence.MyDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import org.koin.core.KoinComponent

class UserRoomDataSourceImpl(
    private val auth : FirebaseAuth,
    private val userMapper: UserMapper,
    private val database: MyDatabase
) : UserRoomDataSource,
    KoinComponent {

    override suspend fun addUser(userVO: UserVO) {
        database.userDao().insertUser(userMapper.voToEntity(userVO))
        Log.d("test---","add user to Room ${userVO.user_id} --- ${userVO.name}")
    }

    override fun getUser(userId : String?): Flow<Resource<UserVO>> = flow {

        database.userDao().getUser(userId ?: auth.currentUser!!.uid)
            .collect {userEntity->
                Log.d("test---","$userId")
                Log.d("test---","get user from Room ${userEntity.user_id} --- ${userEntity.name}")
                emit(Resource.Success(userMapper.entityToVO(userEntity)))
            }

    }

    override suspend fun deleteUser() {
        database.userDao().deleteUser()
    }

    override suspend fun updateUser(userVO: UserVO) {
        TODO("Not yet implemented")
    }
}