package com.itachi.core.interactors

import com.itachi.core.data.UserRepository

class SignOut(private val userRepository: UserRepository) {
    operator fun invoke() = userRepository.signOut()
}