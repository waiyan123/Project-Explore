package com.itachi.core.data.room

import com.itachi.core.domain.models.AncientVO
import kotlinx.coroutines.flow.Flow

interface AncientRoomDataSource {

    suspend fun addAncient(ancientVO : AncientVO)
    suspend fun addAllAncients(ancientList : List<AncientVO>)
    suspend fun deleteAncient(ancientVO: AncientVO)
    suspend fun deleteAllAncients()
    fun getAncientById(id : String) : Flow<AncientVO>
    fun getAllAncients() : Flow<List<AncientVO>>
    suspend fun update(ancientVO: AncientVO)
}