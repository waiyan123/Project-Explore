package com.itachi.core.domain.usecases

import com.itachi.core.domain.repositories.UserRepository

class GetUserUseCase(private val userRepository: UserRepository) {
    operator fun invoke(userId : String? = null) = userRepository.getUserById(userId)
}