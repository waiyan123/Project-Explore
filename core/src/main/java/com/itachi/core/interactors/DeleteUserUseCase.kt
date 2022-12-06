package com.itachi.core.interactors

import com.itachi.core.data.UserRepository
import com.itachi.core.domain.UserVO

class DeleteUserUseCase(private val userRepository: UserRepository) {
    operator fun invoke(userVO : UserVO) = userRepository.deleteUser(userVO)
}