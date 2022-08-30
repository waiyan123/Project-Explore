package com.itachi.explore.framework

import com.itachi.core.data.db.ViewRoomDataSource
import com.itachi.core.domain.ViewVO
import com.itachi.explore.framework.mappers.ViewMapper
import com.itachi.explore.persistence.MyDatabase
import org.koin.core.KoinComponent
import org.koin.core.inject
import javax.inject.Inject

class ViewRoomDataSourceImpl @Inject constructor(private val viewMapper: ViewMapper) : ViewRoomDataSource,KoinComponent{

    private val database: MyDatabase by inject()

    override suspend fun add(viewVO: ViewVO) {
        database.viewDao().addView(viewMapper.voToEntity(viewVO))
    }

    override suspend fun addAll(viewVoList: List<ViewVO>) {
        database.viewDao().insertViewList(viewMapper.voListToEntityList(viewVoList))
    }

    override suspend fun delete(viewVO: ViewVO) {
        database.viewDao().deleteViewById(viewVO.item_id)
    }

    override suspend fun deleteAll() {
        database.viewDao().deleteViewList()
    }

    override suspend fun get(id: String): ViewVO  = viewMapper.entityToVO(database.viewDao().getViewById(id))

    override suspend fun getAll(): List<ViewVO> = viewMapper.entityListToVOList(database.viewDao().getViewsList())

    override suspend fun update(viewVO: ViewVO) {
        TODO("Not yet implemented")
    }
}