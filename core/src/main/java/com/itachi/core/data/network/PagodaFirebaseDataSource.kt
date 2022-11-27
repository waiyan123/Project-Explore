package com.itachi.core.data.network

import com.itachi.core.common.Resource
import com.itachi.core.domain.AncientVO
import com.itachi.core.domain.ItemVO
import com.itachi.core.domain.PagodaVO
import com.itachi.core.domain.PhotoVO
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