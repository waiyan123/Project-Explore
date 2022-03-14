package com.itachi.core.data.network

import com.itachi.core.domain.AncientVO
import com.itachi.core.domain.ItemVO
import com.itachi.core.domain.PagodaVO

interface PagodaFirebaseDataSource {

    suspend fun getPagodaBanner(
        onSuccess : (List<String>) -> Unit,
        onFailure: (String) -> Unit
    )

    suspend fun getPagodaList(
        onSuccess : (List<PagodaVO>) -> Unit,
        onFailure : (String) -> Unit
    )

    suspend fun getPagodaById(
        pagodaId : String,
        onSuccess : (PagodaVO) -> Unit,
        onFailure : (String) -> Unit
    )

    suspend fun getPagodaListByUserId(
        userId : String,
        onSuccess : (List<PagodaVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    suspend fun deletePagodaById(
        pagodaVO: PagodaVO,
        onSuccess : (String) -> Unit,
        onFailure: (String) -> Unit
    )

    suspend fun deleteAllPagodas(
        pagodaList : List<PagodaVO>,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    )

    suspend fun addPagoda(
        pagodaVO: PagodaVO,
        onSuccess : (String) -> Unit,
        onFailure: (String) -> Unit)

    suspend fun addAllPagodas(
        pagodaList : List<PagodaVO>,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    )

    suspend fun updatePagoda(
        pagodaVO: PagodaVO,
        onSuccess : (String) -> Unit,
        onFailure: (String) -> Unit)
}