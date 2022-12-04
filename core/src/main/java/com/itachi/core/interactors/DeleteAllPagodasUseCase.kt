package com.itachi.core.interactors

import com.itachi.core.data.PagodaRepository
import com.itachi.core.data.PagodaRepositoryImpl
import com.itachi.core.domain.PagodaVO

class DeleteAllPagodasUseCase(private val pagodaRepository: PagodaRepository) {

    suspend operator fun invoke() = pagodaRepository.deleteAllPagodas()
}