package com.itachi.core.data

import com.itachi.core.data.db.ViewRoomDataSource
import com.itachi.core.data.network.ViewFirebaseDataSource
import com.itachi.core.domain.PagodaVO
import com.itachi.core.domain.UploadedPhotoVO
import com.itachi.core.domain.ViewVO

class ViewRepository(
    private val viewFirebaseDataSource: ViewFirebaseDataSource,
    private val viewRoomDataSource: ViewRoomDataSource
) {
    suspend fun getPhotoViews(
        onSuccess: (uploadedPhotoList: ArrayList<UploadedPhotoVO>) -> Unit,
        onFailure: (error: String) -> Unit
    ) = viewFirebaseDataSource.getPhotoViews(onSuccess,onFailure)

    suspend fun getViewsList(
        onSuccess : (List<ViewVO>) -> Unit,
        onFailure : (String) -> Unit
    ) = viewFirebaseDataSource.getViewsList(onSuccess,onFailure)

    suspend fun getViewById(
        viewId : String,
        onSuccess : (ViewVO) -> Unit,
        onFailure : (String) -> Unit
    ) = viewFirebaseDataSource.getViewById(viewId,onSuccess,onFailure)

    suspend fun getViewsListByUserId(
        userId : String,
        onSuccess : (List<ViewVO>) -> Unit,
        onFailure: (String) -> Unit
    ) = viewFirebaseDataSource.getViewsListByUserId(userId,onSuccess,onFailure)

    suspend fun deleteViewById(
        viewVO: ViewVO,
        onSuccess : (String) -> Unit,
        onFailure: (String) -> Unit
    ) = viewFirebaseDataSource.deleteViewById(viewVO,onSuccess,onFailure)

    suspend fun addView(
        viewVO: ViewVO ,
        onSuccess : (String) -> Unit,
        onFailure: (String) -> Unit
    ) = viewFirebaseDataSource.addView(viewVO,onSuccess,onFailure)

    suspend fun updateView(
        viewVO: ViewVO,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) = viewFirebaseDataSource.updateView(viewVO,onSuccess,onFailure)

    suspend fun add(viewVO : ViewVO) = viewRoomDataSource.add(viewVO)
    suspend fun addAll(viewVoList : List<ViewVO>) = viewRoomDataSource.addAll(viewVoList)
    suspend fun delete(viewVO: ViewVO) = viewRoomDataSource.delete(viewVO)
    suspend fun deleteAll(viewVoList: List<ViewVO>) = viewRoomDataSource.deleteAll(viewVoList)
    suspend fun get(id : String) : ViewVO = viewRoomDataSource.get(id)
    suspend fun getAll() : List<ViewVO> = viewRoomDataSource.getAll()
    suspend fun update(viewVO: ViewVO) = viewRoomDataSource.update(viewVO)

}