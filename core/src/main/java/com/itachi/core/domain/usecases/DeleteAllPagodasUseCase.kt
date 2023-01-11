package com.itachi.core.domain.usecases

import com.itachi.core.domain.repositories.PagodaRepository

class DeleteAllPagodasUseCase(private val pagodaRepository: PagodaRepository) {

    suspend operator fun invoke() = pagodaRepository.deleteAllPagodas()
}