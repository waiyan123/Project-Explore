package com.itachi.core.domain.usecases

import com.itachi.core.domain.repositories.AncientRepository

class GetAncientByIdUseCase(private val ancientRepository: AncientRepository) {
    operator fun invoke(ancientId : String) = ancientRepository.getAncientById(ancientId)
}