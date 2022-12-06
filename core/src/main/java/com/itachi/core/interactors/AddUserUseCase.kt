package com.itachi.core.interactors

import com.itachi.core.data.UserRepository
import com.itachi.core.domain.UserVO

class AddUserUseCase(private val userRepository: UserRepository) {
    operator fun invoke(userVO : UserVO) = userRepository.addUser(userVO)
}