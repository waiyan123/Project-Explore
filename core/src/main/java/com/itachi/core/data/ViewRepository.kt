package com.itachi.core.data

import com.itachi.core.data.db.ViewRoomDataSource
import com.itachi.core.data.network.ViewFirebaseDataSource
import com.itachi.core.domain.PagodaVO
import com.itachi.core.domain.UploadedPhotoVO
import com.itachi.core.domain.ViewVO
import javax.inject.Inject

class ViewRepository @Inject constructor(
    private val viewFirebaseDataSource: ViewFirebaseDataSource,
    private val viewRoomDataSource: ViewRoomDataSource
) {
    suspend fun getAllPhotoViewsFromFirebase(
        onSuccess: (uploadedPhotoList: List<UploadedPhotoVO>) -> Unit,
        onFailure: (error: String) -> Unit
    ) = viewFirebaseDataSource.getPhotoViews(onSuccess,onFailure)

    suspend fun getAllViewsFromFirebase(
        onSuccess : (List<ViewVO>) -> Unit,
        onFailure : (String) -> Unit
    ) = viewFirebaseDataSource.getViewsList(onSuccess,onFailure)

    suspend fun getViewByIdFromFirebase(
        viewId : String,
        onSuccess : (ViewVO) -> Unit,
        onFailure : (String) -> Unit
    ) = viewFirebaseDataSource.getViewById(viewId,onSuccess,onFailure)

    suspend fun getViewsListByUserIdFromFirebase(
        userId : String,
        onSuccess : (List<ViewVO>) -> Unit,
        onFailure: (String) -> Unit
    ) = viewFirebaseDataSource.getViewsListByUserId(userId,onSuccess,onFailure)

    suspend fun deleteViewFromFirebase(
        viewVO: ViewVO,
        onSuccess : (String) -> Unit,
        onFailure: (String) -> Unit
    ) = viewFirebaseDataSource.deleteViewById(viewVO,onSuccess,onFailure)

    suspend fun addViewToFirebase(
        viewVO: ViewVO ,
        onSuccess : (String) -> Unit,
        onFailure: (String) -> Unit
    ) = viewFirebaseDataSource.addView(viewVO,onSuccess,onFailure)

    suspend fun updateViewToFirebase(
        viewVO: ViewVO,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) = viewFirebaseDataSource.updateView(viewVO,onSuccess,onFailure)

    suspend fun addViewToRoom(viewVO : ViewVO) = viewRoomDataSource.add(viewVO)
    suspend fun addAllViewsToRoom(viewVoList : List<ViewVO>) = viewRoomDataSource.addAll(viewVoList)
    suspend fun deleteViewFromRoom(viewVO: ViewVO) = viewRoomDataSource.delete(viewVO)
    suspend fun deleteAllViewsFromRoom() = viewRoomDataSource.deleteAll()
    suspend fun getViewByIdFromRoom(id : String) = viewRoomDataSource.get(id)
    suspend fun getAllViewsFromRoom() = viewRoomDataSource.getAll()
    suspend fun updateViewToRoom(viewVO: ViewVO) = viewRoomDataSource.update(viewVO)

}