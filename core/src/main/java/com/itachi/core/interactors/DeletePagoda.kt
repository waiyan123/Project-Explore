package com.itachi.core.interactors

import com.itachi.core.data.PagodaRepository
import com.itachi.core.domain.PagodaVO

class DeletePagoda(private val pagodaRepository: PagodaRepository) {

    suspend fun fromRoom(pagodaVO : PagodaVO) = pagodaRepository.deletePagodaFromRoom(pagodaVO)

    suspend fun fromFirebase(
        pagodaVO: PagodaVO,
        onSuccess : (String) -> Unit,
        onFailure: (String) -> Unit
    ) = pagodaRepository.deletePagodaFromFirebase(pagodaVO,onSuccess,onFailure)


}