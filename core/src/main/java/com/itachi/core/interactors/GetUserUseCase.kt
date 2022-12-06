package com.itachi.core.interactors

import com.itachi.core.data.UserRepository

class GetUserUseCase(private val userRepository: UserRepository) {
    operator fun invoke(userId : String? = null) = userRepository.getUserById(userId)
}