package com.itachi.core.interactors

import com.itachi.core.data.PagodaRepository
import com.itachi.core.domain.PagodaVO

class GetAllPagodas(private val pagodaRepository: PagodaRepository) {

    suspend fun fromRoom() = pagodaRepository.getAllPagodasFromRoom()

    suspend fun fromFirebase(
        onSuccess : (List<PagodaVO>) -> Unit,
        onFailure : (String) -> Unit
    ) = pagodaRepository.getAllPagodasFromFirebase(onSuccess,onFailure)
}