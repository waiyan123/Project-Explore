package com.itachi.core.domain.usecases

import com.itachi.core.domain.repositories.AncientRepository
import com.itachi.core.domain.models.AncientVO

class UpdateAncientUseCase(private val ancientRepository: AncientRepository) {
    operator fun invoke(ancientVO: AncientVO) = ancientRepository.updateAncient(ancientVO)
}