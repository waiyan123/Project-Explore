package com.itachi.explore.framework

import com.itachi.core.data.db.ViewRoomDataSource
import com.itachi.core.domain.ViewVO
import com.itachi.explore.framework.mappers.ViewMapper
import com.itachi.explore.persistence.MyDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.koin.core.KoinComponent
import org.koin.core.inject
import javax.inject.Inject

class ViewRoomDataSourceImpl (
    private val viewMapper: ViewMapper,
    private val database : MyDatabase
    ) : ViewRoomDataSource{

    override suspend fun addView(viewVO: ViewVO) {
        database.viewDao().addView(viewMapper.voToEntity(viewVO))
    }

    override suspend fun addAllViews(viewVoList: List<ViewVO>) {
        database.viewDao().insertViewList(viewMapper.voListToEntityList(viewVoList))
    }

    override suspend fun deleteView(viewVO: ViewVO) {
        database.viewDao().deleteViewById(viewVO.item_id)
    }

    override suspend fun deleteAllViews() {
        database.viewDao().deleteViewList()
    }

    override suspend fun getViewById(id: String): ViewVO  = viewMapper.entityToVO(database.viewDao().getViewById(id))

    override suspend fun getAllViews(): Flow<List<ViewVO>> = flowOf(viewMapper.entityListToVOList(database.viewDao().getViewsList()))

    override suspend fun updateView(viewVO: ViewVO) {
        database.viewDao().addView(viewMapper.voToEntity(viewVO))
    }
}