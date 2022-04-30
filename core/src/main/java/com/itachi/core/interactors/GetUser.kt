package com.itachi.core.interactors

import com.itachi.core.data.UserRepository
import com.itachi.core.domain.UserVO

class GetUser(private val userRepository: UserRepository) {
    suspend fun fromRoom(
        onSuccess: (UserVO) -> Unit,
        onFailure: (String) -> Unit
    ) = userRepository.getUserFromRoom(onSuccess,onFailure)

    suspend fun fromFirebase(
        userVO: UserVO,
        onSuccess: (UserVO) -> Unit,
        onFailure: (String) -> Unit
    ) = userRepository.getUserFromFirebase(userVO,onSuccess,onFailure)
}