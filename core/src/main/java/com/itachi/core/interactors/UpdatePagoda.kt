package com.itachi.core.interactors

import com.itachi.core.data.PagodaRepository
import com.itachi.core.domain.PagodaVO

class UpdatePagoda(private val pagodaRepository: PagodaRepository) {

    suspend fun toRoom(pagodaVO : PagodaVO) = pagodaRepository.updatePagodaToRoom(pagodaVO)

    suspend fun toFirebase(
        pagodaVO: PagodaVO,
        onSuccess : (String) -> Unit,
        onFailure: (String) -> Unit
    ) = pagodaRepository.updatePagodaToFirebase(pagodaVO,onSuccess,onFailure)
}