package com.itachi.explore.framework

import com.itachi.core.data.network.ViewFirebaseDataSource
import com.itachi.core.domain.UploadedPhotoVO
import com.itachi.core.domain.ViewVO

class ViewFirebaseDataSourceImpl : ViewFirebaseDataSource{
    override suspend fun getPhotoViews(
        onSuccess: (uploadedPhotoList: ArrayList<UploadedPhotoVO>) -> Unit,
        onFailure: (error: String) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun getViewsList(
        onSuccess: (List<ViewVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun getViewById(
        viewId: String,
        onSuccess: (ViewVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun getViewsListByUserId(
        userId: String,
        onSuccess: (List<ViewVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteViewById(
        viewVO: ViewVO,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun addView(
        viewVO: ViewVO,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun updateView(
        viewVO: ViewVO,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        TODO("Not yet implemented")
    }
}