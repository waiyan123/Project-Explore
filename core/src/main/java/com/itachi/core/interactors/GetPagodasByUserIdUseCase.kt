package com.itachi.core.interactors

import com.itachi.core.data.PagodaRepository

class GetPagodasByUserIdUseCase(
    private val pagodaRepository: PagodaRepository
) {
    operator fun invoke(id : String) = pagodaRepository.getPagodaListByUploaderId(id)
}