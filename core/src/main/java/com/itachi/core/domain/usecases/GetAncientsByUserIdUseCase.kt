package com.itachi.core.domain.usecases

import com.itachi.core.domain.repositories.AncientRepository

class GetAncientsByUserIdUseCase(
    private val ancientRepository: AncientRepository
) {
    operator fun invoke(id : String) = ancientRepository.getAncientsListByUserId(id)
}