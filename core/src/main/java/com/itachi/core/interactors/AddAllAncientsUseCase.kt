package com.itachi.core.interactors

import com.itachi.core.data.AncientRepository
import com.itachi.core.domain.AncientVO

class AddAllAncientsUseCase (private val ancientRepository: AncientRepository) {
    suspend operator fun invoke(ancientList : List<AncientVO>) = ancientRepository.addAllAncients(ancientList)
}