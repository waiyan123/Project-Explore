package com.itachi.core.interactors

import com.itachi.core.data.PagodaRepository
import com.itachi.core.data.PagodaRepositoryImpl
import com.itachi.core.domain.PagodaVO
import com.itachi.core.domain.PhotoVO

class UpdatePagodaUseCase(private val pagodaRepository: PagodaRepository) {

    operator fun invoke(pagodaVO: PagodaVO) = pagodaRepository.updatePagoda(pagodaVO)

}