package com.itachi.core.interactors

import com.itachi.core.data.UserRepository

class GetUser(private val userRepository: UserRepository) {
    suspend operator fun invoke() = userRepository.getUser()
}