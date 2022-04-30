package com.itachi.core.interactors

import com.itachi.core.data.UserRepository
import com.itachi.core.domain.UserVO

class DeleteUser(private val userRepository: UserRepository) {
    suspend fun fromRoom() = userRepository.deleteUserFromRoom()

    suspend fun fromFirebase(
        userVO: UserVO,
        onSuccess: (UserVO) -> Unit,
        onFailure: (String) -> Unit
    ) = userRepository.deleteUserFromFirebase(userVO,onSuccess,onFailure)
}