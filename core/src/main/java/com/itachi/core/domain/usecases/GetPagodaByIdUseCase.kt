package com.itachi.core.domain.usecases

import com.itachi.core.domain.repositories.PagodaRepository

class GetPagodaByIdUseCase(private val pagodaRepository: PagodaRepository) {

    operator fun invoke(pagodaId : String) = pagodaRepository.getPagodaById(pagodaId)

}