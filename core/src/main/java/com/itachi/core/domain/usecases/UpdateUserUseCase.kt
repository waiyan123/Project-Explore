package com.itachi.core.domain.usecases

import com.itachi.core.domain.repositories.UserRepository
import com.itachi.core.domain.models.UserVO

class UpdateUserUseCase(private val userRepository: UserRepository) {
    operator fun invoke(userVO: UserVO) = userRepository.updateUser(userVO)
}