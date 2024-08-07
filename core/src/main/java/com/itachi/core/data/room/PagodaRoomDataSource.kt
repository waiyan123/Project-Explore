package com.itachi.core.data.room

import com.itachi.core.domain.models.PagodaVO
import kotlinx.coroutines.flow.Flow

interface PagodaRoomDataSource {
    suspend fun addPagoda(pagodaVO: PagodaVO)
    suspend fun deletePagoda(pagodaVO: PagodaVO)
    fun getPagodaById(id : String) : Flow<PagodaVO>
    fun getAllPagodas() : Flow<List<PagodaVO>>
    suspend fun updatePagoda(pagodaVO: PagodaVO)
    suspend fun addAllPagodas(pagodaList : List<PagodaVO>)
    suspend fun deleteAllPagodas()
}