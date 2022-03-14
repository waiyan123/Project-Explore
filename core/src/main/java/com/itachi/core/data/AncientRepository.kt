package com.itachi.core.data

import com.itachi.core.data.db.AncientRoomDataSource
import com.itachi.core.data.network.AncientFirebaseDataSource
import com.itachi.core.domain.AncientVO
import com.itachi.core.domain.ItemVO

class AncientRepository(
    private val ancientFirebaseDataSource: AncientFirebaseDataSource,
    private val ancientRoomDataSource: AncientRoomDataSource
) {

    //firebase
    suspend fun getAncientBackgroundFromFirebase(
        bgUrl: (String) -> Unit,
        onFailure: (String) -> Unit
    ) = ancientFirebaseDataSource.getAncientBackground(bgUrl, onFailure)

    suspend fun getAllAncientsFromFirebase(
        onSuccess: (List<AncientVO>) -> Unit,
        onFailure: (String) -> Unit
    ) = ancientFirebaseDataSource.getAncientList(onSuccess, onFailure)

    suspend fun getAncientByIdFromFirebase(
        id: String,
        onSuccess: (AncientVO) -> Unit,
        onFailure: (String) -> Unit
    ) = ancientFirebaseDataSource.getAncientById(id, onSuccess, onFailure)

    suspend fun getAncientListByUserIdFromFirebase(
        userId: String,
        onSuccess: (List<AncientVO>) -> Unit,
        onFailure: (String) -> Unit
    ) = ancientFirebaseDataSource.getAncientsListByUserId(userId, onSuccess, onFailure)

    suspend fun deleteAncientFromFirebase(
        ancientVO: AncientVO,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) = ancientFirebaseDataSource.deleteAncientById(ancientVO, onSuccess, onFailure)

    suspend fun deleteAllAncientsFromFirebase(
        ancientList : List<AncientVO>,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) = ancientFirebaseDataSource.deleteAllAncients(ancientList,onSuccess,onFailure)

    suspend fun addAncientToFirebase(
        ancientVO: AncientVO,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) = ancientFirebaseDataSource.addAncient(ancientVO, onSuccess, onFailure)

    suspend fun addAllAncientsToFirebase(
        ancientList : List<AncientVO>,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) = ancientFirebaseDataSource.addAllAncients(ancientList,onSuccess,onFailure)

    suspend fun updateAncientToFirebase(
        ancientVO: AncientVO,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) = ancientFirebaseDataSource.updateAncient(ancientVO, onSuccess, onFailure)


    //Room
    suspend fun addAncientToRoom(ancientVO: AncientVO) = ancientRoomDataSource.add(ancientVO)

    suspend fun addAllAncientsToRoom(ancientList : List<AncientVO>) = ancientRoomDataSource.addAll(ancientList)

    suspend fun deleteAncientFromRoom(ancientVO: AncientVO) = ancientRoomDataSource.delete(ancientVO)

    suspend fun deleteAllAncientsFromRoom(ancientList: List<AncientVO>) = ancientRoomDataSource.deleteAll(ancientList)

    suspend fun getAncientByIdFromRoom(id: String) = ancientRoomDataSource.get(id)

    suspend fun getAllAncientsFromRoom() = ancientRoomDataSource.getAll()

    suspend fun updateAncientToRoom(ancientVO: AncientVO) = ancientRoomDataSource.update(ancientVO)

}