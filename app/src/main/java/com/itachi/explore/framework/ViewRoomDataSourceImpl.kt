package com.itachi.explore.framework

import com.itachi.core.data.room.ViewRoomDataSource
import com.itachi.core.domain.models.ViewVO
import com.itachi.explore.framework.mappers.ViewMapper
import com.itachi.explore.persistence.MyDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ViewRoomDataSourceImpl (
    private val viewMapper: ViewMapper,
    private val database : MyDatabase
    ) : ViewRoomDataSource{

    override suspend fun addView(viewVO: ViewVO) {
        withContext(Dispatchers.IO){
            database.viewDao().addView(viewMapper.voToEntity(viewVO))
        }
    }

    override suspend fun addAllViews(viewVoList: List<ViewVO>) {
        withContext(Dispatchers.IO) {
            database.viewDao().insertViewList(viewMapper.voListToEntityList(viewVoList))
        }
    }

    override suspend fun deleteView(viewVO: ViewVO) {
        withContext(Dispatchers.IO){
            database.viewDao().deleteViewById(viewVO.item_id)
        }
    }

    override suspend fun deleteAllViews() {
        withContext(Dispatchers.IO){
            database.viewDao().deleteViewList()
        }
    }

    override fun getViewById(id: String): Flow<ViewVO>  =
        database.viewDao().getViewById(id)
            .map { viewMapper.entityToVO(it) }
            .flowOn(Dispatchers.IO)

    override fun getAllViews(): Flow<List<ViewVO>> =
        database.viewDao().getViewsList()
            .map { viewMapper.entityListToVOList(it) }
            .flowOn(Dispatchers.IO)

    override suspend fun updateView(viewVO: ViewVO) {
        withContext(Dispatchers.IO){
            database.viewDao().addView(viewMapper.voToEntity(viewVO))
        }
    }
}