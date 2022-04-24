package com.itachi.core.data.network

import com.itachi.core.data.FirestoreResult
import com.itachi.core.domain.AncientVO
import com.itachi.core.domain.ItemVO
import kotlinx.coroutines.flow.Flow

interface AncientFirebaseDataSource {
    suspend fun getAncientBackground(
        bgUrl : (String) -> Unit,
        onFailure: (String) -> Unit
    )

    suspend fun getAncientList(
        onSuccess : (List<AncientVO>) -> Unit,
        onFailure : (String) -> Unit
    ) : Flow<FirestoreResult<List<AncientVO>>>

    suspend fun getAncientById(
        ancientId : String,
        onSuccess : (AncientVO) -> Unit,
        onFailure : (String) -> Unit
    )

    suspend fun getAncientsListByUserId(
        userId : String,
        onSuccess : (List<AncientVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    suspend fun deleteAncientById(
        ancientVO: AncientVO,
        onSuccess : (String) -> Unit,
        onFailure: (String) -> Unit
    )

    suspend fun deleteAllAncients(
        ancientList : List<AncientVO>,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    )

    suspend fun addAncient(
        ancientVO : AncientVO,
        onSuccess : (String) -> Unit,
        onFailure: (String) -> Unit)

    suspend fun addAllAncients(
        ancientList : List<AncientVO>,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    )

    suspend fun updateAncient(
        ancientVO : AncientVO,
        onSuccess : (String) -> Unit,
        onFailure: (String) -> Unit)
}