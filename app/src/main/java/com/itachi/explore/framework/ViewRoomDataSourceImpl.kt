package com.itachi.explore.framework

import android.util.Log
import com.itachi.core.data.db.ViewRoomDataSource
import com.itachi.core.domain.ViewVO
import com.itachi.explore.framework.mappers.ViewMapper
import com.itachi.explore.persistence.MyDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
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

    override fun getViewById(id: String): Flow<ViewVO>  = database.viewDao().getViewById(id).map { viewMapper.entityToVO(it) }

    override fun getAllViews(): Flow<List<ViewVO>> = database.viewDao().getViewsList().map { viewMapper.entityListToVOList(it) }

    override suspend fun updateView(viewVO: ViewVO) {
        database.viewDao().addView(viewMapper.voToEntity(viewVO))
    }
}