package com.itachi.core.data

import com.itachi.core.domain.PagodaVO
import com.itachi.core.domain.ViewVO

class ViewRepository(private val viewDataSource: ViewDataSource) {

    suspend fun addView(viewVO: ViewVO) = viewDataSource.add(viewVO)

    suspend fun deleteView(viewVO: ViewVO) = viewDataSource.delete(viewVO)

    suspend fun getView(id : String) = viewDataSource.get(id)

    suspend fun getAllViews() = viewDataSource.getAll()

    suspend fun updateView(viewVO: ViewVO) = viewDataSource.update(viewVO)

}