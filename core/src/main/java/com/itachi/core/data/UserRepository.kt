package com.itachi.core.data

import com.itachi.core.data.db.UserRoomDataSource
import com.itachi.core.data.network.UserFirebaseDataSource
import com.itachi.core.domain.UserVO

class UserRepository(
    private val userRoomDataSource: UserRoomDataSource,
    private val userFirebaseDataSource: UserFirebaseDataSource
) {

    //Firebase
    suspend fun addUserToFirebase(
        userVO: UserVO,
        onSuccess: (UserVO) -> Unit,
        onFailure: (String) -> Unit
    ) = userFirebaseDataSource.addUser(userVO, onSuccess, onFailure)

    suspend fun getUserFromFirebase(
        userVO: UserVO,
        onSuccess: (UserVO) -> Unit,
        onFailure: (String) -> Unit
    ) = userFirebaseDataSource.getUser(userVO, onSuccess, onFailure)

    suspend fun deleteUserFromFirebase(
        userVO: UserVO,
        onSuccess: (UserVO) -> Unit,
        onFailure: (String) -> Unit
    ) = userFirebaseDataSource.delete(userVO, onSuccess, onFailure)

    suspend fun updateUserToFirebase(
        userVO: UserVO,
        onSuccess: (UserVO) -> Unit,
        onFailure: (String) -> Unit
    ) = userFirebaseDataSource.update(userVO, onSuccess, onFailure)


    //Room
    suspend fun addUserToRoom(
        userVO: UserVO,
        onSuccess: (UserVO) -> Unit,
        onFailure: (String) -> Unit
    ) = userRoomDataSource.add(userVO,onSuccess,onFailure)

    suspend fun getUserFromRoom(
        onSuccess: (UserVO) -> Unit,
        onFailure: (String) -> Unit
    ) = userRoomDataSource.get(onSuccess,onFailure)

    suspend fun deleteUserFromRoom() = userRoomDataSource.delete()

    suspend fun updateUserToRoom(
        userVO: UserVO,
        onSuccess: (UserVO) -> Unit,
        onFailure: (String) -> Unit
    ) = userRoomDataSource.update(userVO,onSuccess,onFailure)

}