package com.itachi.core.data

import com.itachi.core.data.db.UserRoomDataSource
import com.itachi.core.data.network.UserFirebaseDataSource
import com.itachi.core.domain.UserVO

class UserRepository(
    private val userRoomDataSource: UserRoomDataSource,
    private val userFirebaseDataSource: UserFirebaseDataSource
) {

    //Firebase
    suspend fun addUserToFirebase(userVO: UserVO) = userFirebaseDataSource.add(userVO)

    suspend fun getUserFromFirebase() = userFirebaseDataSource.get()

    suspend fun deleteUserFromFirebase(userVO: UserVO) = userFirebaseDataSource.delete(userVO)

    suspend fun updateUserToFirebase(userVO: UserVO) = userFirebaseDataSource.update(userVO)


    //Room
    suspend fun addUserToRoom(userVO: UserVO) = userRoomDataSource.add(userVO)

    suspend fun getUserFromRoom() = userRoomDataSource.get()

    suspend fun deleteUserFromRoom(userVO: UserVO) = userRoomDataSource.delete(userVO)

    suspend fun updateUserToRoom(userVO: UserVO) = userRoomDataSource.update(userVO)

}