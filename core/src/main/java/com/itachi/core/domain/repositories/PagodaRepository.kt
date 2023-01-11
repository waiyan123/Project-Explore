package com.itachi.core.domain.repositories

import com.itachi.core.common.Resource
import com.itachi.core.domain.models.PagodaVO
import kotlinx.coroutines.flow.Flow

interface PagodaRepository {

    fun getPagodaBanner() : Flow<Resource<List<String>>>

    fun getAllPagodas() : Flow<Resource<List<PagodaVO>>>

    fun getPagodaById(pagodaId : String) : Flow<Resource<PagodaVO>>

    fun getPagodaListByUploaderId(uploaderId : String) : Flow<Resource<List<PagodaVO>>>

    fun deletePagoda(pagodaVO: PagodaVO) : Flow<Resource<String>>

    suspend fun deleteAllPagodas()

    fun addPagoda(pagodaVO: PagodaVO) : Flow<Resource<String>>

    fun updatePagoda(pagodaVO: PagodaVO) : Flow<Resource<String>>
}