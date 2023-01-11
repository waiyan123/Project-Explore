package com.itachi.core.domain.usecases

import com.itachi.core.domain.repositories.AncientRepository
import com.itachi.core.domain.models.AncientVO

class AddAncientUseCase(private val ancientRepository: AncientRepository) {
    operator fun invoke(ancientVO: AncientVO) = ancientRepository.addAncient(ancientVO)
}