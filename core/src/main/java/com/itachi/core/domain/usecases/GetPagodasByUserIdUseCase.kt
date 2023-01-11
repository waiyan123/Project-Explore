package com.itachi.core.domain.usecases

import com.itachi.core.domain.repositories.PagodaRepository

class GetPagodasByUserIdUseCase(
    private val pagodaRepository: PagodaRepository
) {
    operator fun invoke(id : String) = pagodaRepository.getPagodaListByUploaderId(id)
}