package com.itachi.core.interactors

import com.itachi.core.data.AncientRepository

class GetAncientsByUserIdUseCase(
    private val ancientRepository: AncientRepository
) {
    operator fun invoke(id : String) = ancientRepository.getAncientsListByUserId(id)
}