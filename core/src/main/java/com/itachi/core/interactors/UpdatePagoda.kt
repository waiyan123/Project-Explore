package com.itachi.core.interactors

import com.itachi.core.data.PagodaRepository
import com.itachi.core.domain.PagodaVO
import com.itachi.core.domain.PhotoVO

class UpdatePagoda(private val pagodaRepository: PagodaRepository) {

    suspend fun toRoom(pagodaVO : PagodaVO) = pagodaRepository.updatePagodaToRoom(pagodaVO)

    suspend fun toFirebase(
        photoVOList : List<PhotoVO>,
        byteArrayList: ArrayList<ByteArray>,
        geoPointsList: ArrayList<String>,
        pagodaVO: PagodaVO,
        onSuccess : (String) -> Unit,
        onFailure: (String) -> Unit
    ) = pagodaRepository.updatePagodaToFirebase(photoVOList,byteArrayList,geoPointsList,pagodaVO,onSuccess,onFailure)
}