package com.itachi.core.domain.usecases

import com.itachi.core.domain.repositories.UserRepository

class SignOut(private val userRepository: UserRepository) {
    operator fun invoke() = userRepository.signOut()
}