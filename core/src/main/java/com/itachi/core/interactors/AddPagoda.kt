package com.itachi.core.interactors

import com.itachi.core.data.PagodaRepository
import com.itachi.core.domain.PagodaVO

class AddPagoda(private val pagodaRepository: PagodaRepository) {

    suspend fun toRoom(pagodavO : PagodaVO) = pagodaRepository.addPagodaToRoom(pagodavO)

    suspend fun toFirebase(
        pagodaVO: PagodaVO,
        onSuccess : (String) -> Unit,
        onFailure: (String) -> Unit
    ) = pagodaRepository.addPagodaToFirebase(pagodaVO,onSuccess,onFailure)
}