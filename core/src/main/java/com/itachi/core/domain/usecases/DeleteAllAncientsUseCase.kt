package com.itachi.core.domain.usecases

import com.itachi.core.domain.repositories.AncientRepository

class DeleteAllAncientsUseCase(private val ancientRepository: AncientRepository) {
    suspend operator fun invoke() = ancientRepository.deleteAllAncients()
}