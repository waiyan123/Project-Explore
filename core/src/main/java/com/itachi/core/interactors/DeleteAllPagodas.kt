package com.itachi.core.interactors

import com.itachi.core.data.PagodaRepository
import com.itachi.core.domain.PagodaVO

class DeleteAllPagodas(private val pagodaRepository: PagodaRepository) {

    suspend fun fromRoom() = pagodaRepository.deleteAllPagodasFromRoom()

    suspend fun fromFirebase(
        pagodaList : List<PagodaVO>,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) = pagodaRepository.deleteAllPagodasFromFirebase(pagodaList,onSuccess,onFailure)
}