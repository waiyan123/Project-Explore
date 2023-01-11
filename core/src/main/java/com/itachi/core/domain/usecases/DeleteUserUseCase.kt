package com.itachi.core.domain.usecases

import com.itachi.core.domain.repositories.UserRepository
import com.itachi.core.domain.models.UserVO

class DeleteUserUseCase(private val userRepository: UserRepository) {
    operator fun invoke(userVO : UserVO) = userRepository.deleteUser(userVO)
}