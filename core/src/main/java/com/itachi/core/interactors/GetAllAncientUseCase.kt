package com.itachi.core.interactors

import com.itachi.core.data.AncientRepository

class GetAllAncientUseCase(private val ancientRepository: AncientRepository) {
    operator fun invoke() = ancientRepository.getAllAncients()
}