package com.itachi.core.interactors

import com.itachi.core.data.AncientRepository
import com.itachi.core.domain.AncientVO

class GetAncientByIdUseCase(private val ancientRepository: AncientRepository) {
    operator fun invoke(ancientId : String) = ancientRepository.getAncientById(ancientId)
}