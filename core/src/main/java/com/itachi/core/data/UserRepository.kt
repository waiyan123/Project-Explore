package com.itachi.core.data

import com.itachi.core.data.db.UserRoomDataSource
import com.itachi.core.data.network.UserFirebaseDataSource
import com.itachi.core.domain.UserVO
import com.itachi.core.common.Resource
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

class UserRepository(
    private val userFirebaseDataSource: UserFirebaseDataSource,
    private val userRoomDataSource: UserRoomDataSource
) : UserDataSource {

    override fun addUser(userVO: UserVO): Flow<Resource<UserVO>> = flow {
        userFirebaseDataSource.addUser(userVO).collect { resource ->
            resource.data?.let {
                userRoomDataSource.addUser(it)
                emit(resource)
            }
        }
    }

    @OptIn(FlowPreview::class)
    override fun getUserById(userId: String?): Flow<Resource<UserVO>>{

        return userRoomDataSource.getUser(userId)
            .flatMapConcat {resourceFromRoom->
                flow {
                    emit(resourceFromRoom)
                    userFirebaseDataSource.getUserById(userId)
                        .collect {resourceFromFirebase->
                            resourceFromFirebase.data?.let { userVO->
                                userRoomDataSource.addUser(userVO)
                            }
                            emit(resourceFromFirebase)
                        }
                }
            }

    }

    override fun deleteUser(
        userVO: UserVO
    ): Flow<Resource<String>> = flow {
        userFirebaseDataSource.delete(userVO).collect { resource ->
            resource.data?.let {
                userRoomDataSource.deleteUser()
            }
            emit(resource)
        }
    }

    override fun updateUser(userVO: UserVO): Flow<Resource<UserVO>> = flow {
        userFirebaseDataSource.update(userVO).collect { resource ->
            resource.data?.let {
                userRoomDataSource.updateUser(it)
            }
            emit(resource)
        }
    }

    override fun signOut(): Flow<Resource<String>> = flow {
        userFirebaseDataSource.signOut().collect { resource ->
            resource.data?.let {
                userRoomDataSource.deleteUser()
            }
            emit(resource)
        }
    }

}