package com.itachi.core.data

import com.itachi.core.domain.PagodaVO
import com.itachi.core.domain.ViewVO

interface ViewDataSource {
    suspend fun add(viewVO: ViewVO)
    suspend fun delete(viewVO: ViewVO)
    suspend fun get(id : String) : ViewVO
    suspend fun getAll() : List<ViewVO>
    suspend fun update(viewVO: ViewVO)
}