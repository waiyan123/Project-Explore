package com.itachi.core.interactors

import com.itachi.core.data.UserRepository
import com.itachi.core.domain.UserVO

class UpdateUser(private val userRepository: UserRepository) {

    suspend fun toRoom(
        userVO: UserVO,
        onSuccess: (UserVO) -> Unit,
        onFailure: (String) -> Unit
    ) = userRepository.updateUserToRoom(userVO, onSuccess, onFailure)

    suspend fun toFirebase(
        userVO: UserVO,
        onSuccess: (UserVO) -> Unit,
        onFailure: (String) -> Unit
    ) = userRepository.updateUserToFirebase(userVO,onSuccess,onFailure)
}