package com.itachi.core.interactors

import com.itachi.core.data.UserRepository
import com.itachi.core.domain.UserVO

class UpdateUserUseCase(private val userRepository: UserRepository) {
    operator fun invoke(userVO: UserVO) = userRepository.updateUser(userVO)
}