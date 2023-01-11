package com.itachi.core.domain.usecases

import com.itachi.core.domain.repositories.AncientRepository
import com.itachi.core.domain.models.AncientVO

class AddAllAncientsUseCase (private val ancientRepository: AncientRepository) {
    suspend operator fun invoke(ancientList : List<AncientVO>) = ancientRepository.addAllAncients(ancientList)
}