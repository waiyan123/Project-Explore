package com.itachi.core.data

import com.itachi.core.common.Resource
import com.itachi.core.domain.UploadedPhotoVO
import com.itachi.core.domain.ViewVO
import kotlinx.coroutines.flow.Flow

interface ViewRepository {

    fun addView(viewVO: ViewVO) : Flow<Resource<String>>

    suspend fun addAllViews(viewList : List<ViewVO>)

    fun getViewById(viewId : String) : Flow<Resource<ViewVO>>

    fun getAllViews() : Flow<Resource<List<ViewVO>>>

    fun getAllViewsPhoto() : Flow<Resource<List<UploadedPhotoVO>>>

    fun getViewListByUserId(userId : String) : Flow<Resource<List<ViewVO>>>

    fun deleteView(viewVO: ViewVO) : Flow<Resource<String>>

    suspend fun deleteAllViews()

    fun updateView(viewVO: ViewVO) : Flow<Resource<String>>
}