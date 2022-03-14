package com.itachi.core.interactors

import com.itachi.core.data.PagodaRepository
import com.itachi.core.domain.PagodaVO

class GetPagoda(private val pagodaRepository: PagodaRepository) {

    suspend fun fromRoom(id : String) = pagodaRepository.getPagodaByIdFromRoom(id)

    suspend fun fromFirebase(
        pagodaId : String,
        onSuccess : (PagodaVO) -> Unit,
        onFailure : (String) -> Unit
    ) = pagodaRepository.getPagodaByIdFromFirebase(pagodaId,onSuccess,onFailure)

    suspend fun getBanners(
        onSuccess : (List<String>) -> Unit,
        onFailure: (String) -> Unit
    ) = pagodaRepository.getPagodaBannerFromFirebase(onSuccess,onFailure)

}