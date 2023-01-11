package com.itachi.core.domain.usecases

import com.itachi.core.domain.repositories.AncientRepository

class GetAllAncientUseCase(private val ancientRepository: AncientRepository) {
    operator fun invoke() = ancientRepository.getAllAncients()
}