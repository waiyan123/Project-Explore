package com.itachi.core.data

import com.itachi.core.common.Resource
import com.itachi.core.domain.AncientVO
import kotlinx.coroutines.flow.Flow

interface AncientRepository {

    fun addAncient(ancientVO: AncientVO) : Flow<Resource<String>>

    suspend fun addAllAncients(ancientList : List<AncientVO>)

    fun getAncientBackground() : Flow<Resource<String>>

    fun getAllAncients() : Flow<Resource<List<AncientVO>>>

    fun getAncientById(ancientId : String) : Flow<Resource<AncientVO>>

    fun getAncientsListByUserId(userId : String) : Flow<Resource<List<AncientVO>>>

    fun updateAncient(ancientVO: AncientVO) : Flow<Resource<String>>

    fun deleteAncient(ancientVO: AncientVO) : Flow<Resource<String>>

    suspend fun deleteAllAncients()


}