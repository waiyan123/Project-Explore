package com.itachi.core.data

import com.itachi.core.common.Resource
import com.itachi.core.data.db.ViewRoomDataSource
import com.itachi.core.data.network.ViewFirebaseDataSource
import com.itachi.core.domain.PagodaVO
import com.itachi.core.domain.UploadedPhotoVO
import com.itachi.core.domain.ViewVO
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ViewRepositoryImpl(
    private val viewFirebaseDataSource: ViewFirebaseDataSource,
    private val viewRoomDataSource: ViewRoomDataSource
) : ViewRepository {

    override fun addView(viewVO: ViewVO): Flow<Resource<String>> = flow {
        viewFirebaseDataSource.addView(viewVO)
            .collect { resourceFirebase ->
                resourceFirebase.data?.let {
                    viewRoomDataSource.addView(viewVO)
                    emit(resourceFirebase)
                }

            }
    }

    override suspend fun addAllViews(viewList: List<ViewVO>) {
        viewRoomDataSource.addAllViews(viewList)
    }

    override fun getViewById(viewId: String): Flow<Resource<ViewVO>> = flow {
        emit(Resource.Success(viewRoomDataSource.getViewById(viewId)))
        viewFirebaseDataSource.getViewById(viewId)
            .collect {
                emit(it)
            }
    }

    override fun getAllViews(): Flow<Resource<List<ViewVO>>> = flow {
        viewRoomDataSource.getAllViews()
            .onEach {
                emit(Resource.Success(it))
            }
            .flatMapConcat {
                viewFirebaseDataSource.getAllViews()
            }
            .collect { resourceFirebase ->
                resourceFirebase.data?.let {
                    viewRoomDataSource.addAllViews(it)
                    emit(resourceFirebase)
                }
            }
    }

    override fun getAllViewsPhoto(): Flow<Resource<List<UploadedPhotoVO>>> {
        return viewFirebaseDataSource.getViewsPhotos()
    }


    override fun getViewListByUserId(userId: String): Flow<Resource<List<ViewVO>>> {
        return viewFirebaseDataSource.getViewsListByUserId(userId)
    }

    override fun deleteView(viewVO: ViewVO): Flow<Resource<String>> = flow{
        viewFirebaseDataSource.deleteViewById(viewVO)
            .collect {
                viewRoomDataSource.deleteView(viewVO)
                emit(it)
            }
    }

    override suspend fun deleteAllViews() {
        viewRoomDataSource.deleteAllViews()
    }

    override fun updateView(viewVO: ViewVO): Flow<Resource<String>> = flow{
        viewFirebaseDataSource.updateView(viewVO)
            .collect {
                viewRoomDataSource.updateView(viewVO)
                emit(it)
            }
    }

}