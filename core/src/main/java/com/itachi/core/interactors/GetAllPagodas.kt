package com.itachi.core.interactors

import com.itachi.core.data.PagodaRepository
import com.itachi.core.data.PagodaRepositoryImpl
import com.itachi.core.domain.PagodaVO

class GetAllPagodas(private val pagodaRepository: PagodaRepository) {

    operator fun invoke() = pagodaRepository.getAllPagodas()
}