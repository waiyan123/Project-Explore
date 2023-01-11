package com.itachi.core.data

import com.itachi.core.common.Resource
import com.itachi.core.data.room.ViewRoomDataSource
import com.itachi.core.data.firebase.ViewFirebaseDataSource
import com.itachi.core.domain.models.UploadedPhotoVO
import com.itachi.core.domain.models.ViewVO
import com.itachi.core.domain.repositories.ViewRepository
import kotlinx.coroutines.flow.*

class ViewRepositoryImpl(
    private val viewFirebaseDataSource: ViewFirebaseDataSource,
    private val viewRoomDataSource: ViewRoomDataSource
) : ViewRepository {

    override fun addView(viewVO: ViewVO): Flow<Resource<String>> =
        viewFirebaseDataSource.addView(viewVO)
            .onEach { viewRoomDataSource.addView(viewVO) }

    override suspend fun addAllViews(viewList: List<ViewVO>) {
        viewRoomDataSource.addAllViews(viewList)
    }

    override fun getViewById(viewId: String): Flow<Resource<ViewVO>> =
        viewRoomDataSource.getViewById(viewId)
            .flatMapConcat { viewFirebaseDataSource.getViewById(viewId) }


    override fun getAllViews(): Flow<Resource<List<ViewVO>>> =
        viewRoomDataSource.getAllViews()
            .flatMapConcat {
                viewFirebaseDataSource.getAllViews()
            }
            .onEach { resource ->
                resource.data?.let {
                    viewRoomDataSource.addAllViews(it)
                }
            }

    override fun getAllViewsPhoto(): Flow<Resource<List<UploadedPhotoVO>>> {
        return viewFirebaseDataSource.getViewsPhotos()
    }


    override fun getViewListByUserId(userId: String): Flow<Resource<List<ViewVO>>> {
        return viewFirebaseDataSource.getViewsListByUserId(userId)
    }

    override fun deleteView(viewVO: ViewVO): Flow<Resource<String>> =
        viewFirebaseDataSource.deleteViewById(viewVO)
            .onEach { viewRoomDataSource.deleteView(viewVO) }

    override suspend fun deleteAllViews() {
        viewRoomDataSource.deleteAllViews()
    }

    override fun updateView(viewVO: ViewVO): Flow<Resource<String>> =
        viewFirebaseDataSource.updateView(viewVO)
            .onEach { viewRoomDataSource.updateView(viewVO) }

}