package com.itachi.core.domain.usecases

import com.itachi.core.domain.repositories.PagodaRepository
import com.itachi.core.domain.models.PagodaVO

class DeletePagodaUseCase(private val pagodaRepository: PagodaRepository) {

    operator fun invoke(pagodaVO: PagodaVO) = pagodaRepository.deletePagoda(pagodaVO)

}