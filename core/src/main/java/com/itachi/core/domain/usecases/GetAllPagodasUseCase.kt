package com.itachi.core.domain.usecases

import com.itachi.core.domain.repositories.PagodaRepository

class GetAllPagodasUseCase(private val pagodaRepository: PagodaRepository) {

    operator fun invoke() = pagodaRepository.getAllPagodas()
}