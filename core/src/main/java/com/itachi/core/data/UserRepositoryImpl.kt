package com.itachi.core.data

import com.itachi.core.data.db.UserRoomDataSource
import com.itachi.core.data.network.UserFirebaseDataSource
import com.itachi.core.domain.UserVO
import com.itachi.core.common.Resource
import kotlinx.coroutines.FlowPreview
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