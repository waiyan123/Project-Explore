package com.itachi.core.data.db

import com.itachi.core.domain.ViewVO

interface ViewRoomDataSource {
    suspend fun add(viewVO : ViewVO)
    suspend fun addAll(viewVoList : List<ViewVO>)
    suspend fun delete(viewVO: ViewVO)
    suspend fun deleteAll()
    suspend fun get(id : String) : ViewVO
    suspend fun getAll() : List<ViewVO>
    suspend fun update(viewVO: ViewVO)
}