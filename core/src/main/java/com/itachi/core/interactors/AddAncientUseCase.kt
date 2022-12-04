package com.itachi.core.interactors

import com.itachi.core.data.AncientRepository
import com.itachi.core.domain.AncientVO

class AddAncientUseCase(private val ancientRepository: AncientRepository) {
    operator fun invoke(ancientVO: AncientVO) = ancientRepository.addAncient(ancientVO)
}