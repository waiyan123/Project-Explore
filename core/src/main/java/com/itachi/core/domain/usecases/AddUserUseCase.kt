package com.itachi.core.domain.usecases

import com.itachi.core.domain.repositories.UserRepository
import com.itachi.core.domain.models.UserVO

class AddUserUseCase(private val userRepository: UserRepository) {
    operator fun invoke(userVO : UserVO) = userRepository.addUser(userVO)
}