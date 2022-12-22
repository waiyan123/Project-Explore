package com.itachi.core.data.db

import com.itachi.core.common.Resource
import com.itachi.core.domain.ViewVO
import kotlinx.coroutines.flow.Flow

interface ViewRoomDataSource {
    suspend fun addView(viewVO : ViewVO)
    suspend fun addAllViews(viewVoList : List<ViewVO>)
    suspend fun deleteView(viewVO: ViewVO)
    suspend fun deleteAllViews()
    fun getViewById(id : String) : Flow<ViewVO>
    fun getAllViews() : Flow<List<ViewVO>>
    suspend fun updateView(viewVO: ViewVO)
}