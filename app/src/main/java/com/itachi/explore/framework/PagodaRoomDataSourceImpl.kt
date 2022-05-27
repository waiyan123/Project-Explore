package com.itachi.explore.framework

import android.util.Log
import com.itachi.core.data.db.PagodaRoomDataSource
import com.itachi.core.domain.PagodaVO
import com.itachi.explore.framework.mappers.PagodaMapper
import com.itachi.explore.persistence.MyDatabase
import org.koin.core.KoinComponent
import org.koin.core.inject

class PagodaRoomDataSourceImpl(
    private val pagodaMapper: PagodaMapper
) : PagodaRoomDataSource, KoinComponent {

    private val database: MyDatabase by inject()

    override suspend fun add(pagodaVO: PagodaVO) {
        database.pagodaDao().addPagoda(pagodaMapper.voToEntity(pagodaVO))
    }

    override suspend fun delete(pagodaVO: PagodaVO) {
        database.pagodaDao().deletePagodaById(pagodaVO.item_id)
        Log.d("test---","deleted pagoda item")
    }

    override suspend fun get(id: String) = pagodaMapper.entityToVO(database.pagodaDao().getPagodaById(id))

    override suspend fun getAll(): List<PagodaVO> = pagodaMapper.entityListToVOList(database.pagodaDao().getPagodaList())

    override suspend fun addAll(pagodaList: List<PagodaVO>) {
        database.pagodaDao().insertPagodaList(pagodaMapper.voListToEntityList(pagodaList))
    }

    override suspend fun deleteAll() {
        database.pagodaDao().deletePagodaList()
    }

    override suspend fun update(pagodaVO: PagodaVO) {
        TODO("Not yet implemented")
    }


}