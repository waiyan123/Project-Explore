package com.itachi.core.interactors

import com.itachi.core.data.AncientRepository
import com.itachi.core.domain.AncientVO

class DeleteAllAncientsUseCase(private val ancientRepository: AncientRepository) {
    suspend operator fun invoke() = ancientRepository.deleteAllAncients()
}