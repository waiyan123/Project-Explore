package com.itachi.core.data.network

import com.itachi.core.domain.UploadedPhotoVO
import com.itachi.core.domain.ViewVO

interface ViewFirebaseDataSource {

    suspend fun getPhotoViews(
        onSuccess: (uploadedPhotoList: ArrayList<UploadedPhotoVO>) -> Unit,
        onFailure: (error: String) -> Unit
    )

    suspend fun getViewsList(
        onSuccess : (List<ViewVO>) -> Unit,
        onFailure : (String) -> Unit
    )

    suspend fun getViewById(
        viewId : String,
        onSuccess : (ViewVO) -> Unit,
        onFailure : (String) -> Unit
    )

    suspend fun getViewsListByUserId(
        userId : String,
        onSuccess : (List<ViewVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    suspend fun deleteViewById(
        viewVO: ViewVO,
        onSuccess : (String) -> Unit,
        onFailure: (String) -> Unit
    )

    suspend fun addView(
        viewVO: ViewVO ,
        onSuccess : (String) -> Unit,
        onFailure: (String) -> Unit
    )

    suspend fun updateView(
        viewVO: ViewVO,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    )
}