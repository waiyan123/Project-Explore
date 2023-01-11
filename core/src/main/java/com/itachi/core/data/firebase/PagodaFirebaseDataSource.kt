package com.itachi.core.data.firebase

import com.itachi.core.common.Resource
import com.itachi.core.domain.models.PagodaVO
import kotlinx.coroutines.flow.Flow

interface PagodaFirebaseDataSource {

    fun getPagodaBanner() : Flow<Resource<List<String>>>

    fun getAllPagodas() : Flow<Resource<List<PagodaVO>>>

    fun getPagodaById(pagodaId : String) : Flow<Resource<PagodaVO>>

    fun getPagodaListByUserId(userId : String) : Flow<Resource<List<PagodaVO>>>

    fun deletePagodaById(pagodaId: String) : Flow<Resource<String>>

    fun addPagoda(pagodaVO: PagodaVO) : Flow<Resource<String>>

    fun updatePagoda(pagodaVO: PagodaVO) : Flow<Resource<String>>
}