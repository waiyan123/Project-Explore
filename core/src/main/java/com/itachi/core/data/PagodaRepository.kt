package com.itachi.core.data

import com.itachi.core.data.db.PagodaRoomDataSource
import com.itachi.core.data.network.PagodaFirebaseDataSource
import com.itachi.core.domain.ItemVO
import com.itachi.core.domain.PagodaVO
import com.itachi.core.domain.PhotoVO

class PagodaRepository(
    private val pagodaFirebaseDataSource: PagodaFirebaseDataSource,
    private val pagodaRoomDataSource: PagodaRoomDataSource
) {

    //Firebase
    suspend fun getPagodaBannerFromFirebase(
        onSuccess : (List<String>) -> Unit,
        onFailure: (String) -> Unit
    ) = pagodaFirebaseDataSource.getPagodaBanner(onSuccess,onFailure)

    suspend fun getAllPagodasFromFirebase(
        onSuccess : (List<PagodaVO>) -> Unit,
        onFailure : (String) -> Unit
    ) = pagodaFirebaseDataSource.getPagodaList(onSuccess,onFailure)

    suspend fun getPagodaByIdFromFirebase(
        pagodaId : String,
        onSuccess : (PagodaVO) -> Unit,
        onFailure : (String) -> Unit
    ) = pagodaFirebaseDataSource.getPagodaById(pagodaId,onSuccess,onFailure)

    suspend fun getPagodaListByUserIdFromFirebase(
        userId : String,
        onSuccess : (List<PagodaVO>) -> Unit,
        onFailure: (String) -> Unit
    ) = pagodaFirebaseDataSource.getPagodaListByUserId(userId,onSuccess,onFailure)

    suspend fun deletePagodaFromFirebase(
        pagodaVO: PagodaVO,
        onSuccess : (String) -> Unit,
        onFailure: (String) -> Unit
    ) = pagodaFirebaseDataSource.deletePagodaById(pagodaVO,onSuccess,onFailure)

    suspend fun deleteAllPagodasFromFirebase(
        pagodaList : List<PagodaVO>,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) = pagodaFirebaseDataSource.deleteAllPagodas(pagodaList,onSuccess,onFailure)

    suspend fun addAllPagodasToFirebase(
        pagodaList : List<PagodaVO>,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) = pagodaFirebaseDataSource.addAllPagodas(pagodaList,onSuccess,onFailure)

    suspend fun addPagodaToFirebase(
        pagodaVO: PagodaVO,
        byteArrayList: ArrayList<ByteArray>,
        geoPointsList: ArrayList<String>,
        onSuccess : (String) -> Unit,
        onFailure: (String) -> Unit
    ) = pagodaFirebaseDataSource.addPagoda(byteArrayList,geoPointsList,pagodaVO,onSuccess,onFailure)

    suspend fun updatePagodaToFirebase(
        photoVOList : List<PhotoVO>,
        byteArrayList: ArrayList<ByteArray>,
        geoPointsList: ArrayList<String>,
        pagodaVO: PagodaVO,
        onSuccess : (String) -> Unit,
        onFailure: (String) -> Unit
    ) = pagodaFirebaseDataSource.updatePagoda(photoVOList,byteArrayList,geoPointsList,pagodaVO,onSuccess,onFailure)

    suspend fun addPagodaToRoom(pagodaVO: PagodaVO) = pagodaRoomDataSource.add(pagodaVO)

    suspend fun addAllPagodasToRoom(pagodaList : List<PagodaVO>) = pagodaRoomDataSource.addAll(pagodaList)

    suspend fun deletePagodaFromRoom(pagodaVO: PagodaVO) = pagodaRoomDataSource.delete(pagodaVO)

    suspend fun deleteAllPagodasFromRoom() = pagodaRoomDataSource.deleteAll()

    suspend fun getPagodaByIdFromRoom(id: String) = pagodaRoomDataSource.get(id)

    suspend fun getAllPagodasFromRoom() = pagodaRoomDataSource.getAll()

    suspend fun updatePagodaToRoom(pagodaVO: PagodaVO) = pagodaRoomDataSource.update(pagodaVO)

}