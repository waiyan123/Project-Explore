package com.itachi.core.data.db

import com.itachi.core.domain.AncientVO

interface AncientRoomDataSource {
    suspend fun add(ancientVO : AncientVO)
    suspend fun addAll(ancientList : List<AncientVO>)
    suspend fun delete(ancientVO: AncientVO)
    suspend fun deleteAll(ancientList: List<AncientVO>)
    suspend fun get(id : String) : AncientVO
    suspend fun getAll() : List<AncientVO>
    suspend fun update(ancientVO: AncientVO)
}