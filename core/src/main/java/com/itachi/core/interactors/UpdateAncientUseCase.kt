package com.itachi.core.interactors

import com.itachi.core.data.AncientRepository
import com.itachi.core.domain.AncientVO

class UpdateAncientUseCase(private val ancientRepository: AncientRepository) {
    operator fun invoke(ancientVO: AncientVO) = ancientRepository.updateAncient(ancientVO)
}