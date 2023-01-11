package com.itachi.core.domain.usecases

import com.itachi.core.domain.repositories.AncientRepository
import com.itachi.core.domain.models.AncientVO

class DeleteAncientUseCase(private val ancientRepository: AncientRepository) {
    operator fun invoke(ancientVO: AncientVO) = ancientRepository.deleteAncient(ancientVO)
}