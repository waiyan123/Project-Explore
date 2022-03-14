package com.itachi.core.interactors

import com.itachi.core.data.PagodaRepository
import com.itachi.core.domain.AncientVO
import com.itachi.core.domain.PagodaVO

class AddAllPagodas(private val pagodaRepository: PagodaRepository) {

    suspend fun toRoom(pagodaList : List<PagodaVO>) = pagodaRepository.addAllPagodasToRoom(pagodaList)

    suspend fun toFirebase(
        pagodaList : List<PagodaVO>,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) = pagodaRepository.addAllPagodasToFirebase(pagodaList,onSuccess,onFailure)
}