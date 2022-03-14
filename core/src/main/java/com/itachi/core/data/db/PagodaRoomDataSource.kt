package com.itachi.core.data.db

import com.itachi.core.domain.AncientVO
import com.itachi.core.domain.PagodaVO

interface PagodaRoomDataSource {
    suspend fun add(pagodaVO: PagodaVO)
    suspend fun delete(pagodaVO: PagodaVO)
    suspend fun get(id : String) : PagodaVO
    suspend fun getAll() : List<PagodaVO>
    suspend fun update(pagodaVO: PagodaVO)
    suspend fun addAll(pagodaList : List<PagodaVO>)
    suspend fun deleteAll()
}