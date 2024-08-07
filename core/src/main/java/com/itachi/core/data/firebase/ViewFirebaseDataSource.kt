package com.itachi.core.data.firebase

import com.itachi.core.common.Resource
import com.itachi.core.domain.models.UploadedPhotoVO
import com.itachi.core.domain.models.ViewVO
import kotlinx.coroutines.flow.Flow

interface ViewFirebaseDataSource {

    fun getViewsPhotos() : Flow<Resource<List<UploadedPhotoVO>>>

    fun getViewById(viewId : String) : Flow<Resource<ViewVO>>

    fun getAllViews() : Flow<Resource<List<ViewVO>>>

    fun getViewsListByUserId(userId : String) : Flow<Resource<List<ViewVO>>>

    fun deleteViewById(viewVO: ViewVO) : Flow<Resource<String>>

    fun addView(viewVO: ViewVO) : Flow<Resource<String>>

    fun updateView(viewVO: ViewVO) : Flow<Resource<String>>
}