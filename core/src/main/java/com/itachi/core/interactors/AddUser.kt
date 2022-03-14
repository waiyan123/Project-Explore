package com.itachi.core.interactors

import com.itachi.core.data.UserRepository
import com.itachi.core.domain.UserVO

class AddUser(private val userRepository: UserRepository) {
    suspend operator fun invoke(userVO: UserVO) = userRepository.addUser(userVO)
}