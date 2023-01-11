package com.itachi.core.data

import com.itachi.core.data.room.UserRoomDataSource
import com.itachi.core.data.firebase.UserFirebaseDataSource
import com.itachi.core.domain.models.UserVO
import com.itachi.core.common.Resource
import com.itachi.core.domain.repositories.UserRepository
import kotlinx.coroutines.flow.*

class UserRepositoryImpl(
    private val userFirebaseDataSource: UserFirebaseDataSource,
    private val userRoomDataSource: UserRoomDataSource
) : UserRepository {

    override fun addUser(userVO: UserVO): Flow<Resource<UserVO>> =
        userFirebaseDataSource.addUser(userVO)
            .onEach { resource ->
                resource.data?.let {
                    userRoomDataSource.addUser(it)
                }
            }

    override fun getUserById(userId: String?): Flow<Resource<UserVO>> =
        userRoomDataSource.getUser(userId)
            .flatMapConcat { userFirebaseDataSource.getUserById(userId) }
            .onEach { resource ->
                resource.data?.let {
                    userRoomDataSource.addUser(it)
                }
            }


    override fun deleteUser(userVO: UserVO): Flow<Resource<String>> =
        userFirebaseDataSource.delete(userVO)
            .onEach { resource ->
                resource.data?.let {
                    userRoomDataSource.deleteUser()
                }
            }

    override fun updateUser(userVO: UserVO): Flow<Resource<UserVO>> =
        userFirebaseDataSource.update(userVO)
            .onEach { resource ->
                resource.data?.let {
                    userRoomDataSource.updateUser(it)
                }
            }

    override fun signOut(): Flow<Resource<String>> =
        userFirebaseDataSource.signOut()
            .onEach {
                userRoomDataSource.deleteUser()
            }

}