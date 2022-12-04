package com.itachi.core.interactors

import com.itachi.core.data.AncientRepository

class GetAncientBackgroundUseCase(private val ancientRepository: AncientRepository) {
    operator fun invoke() = ancientRepository.getAncientBackground()
}