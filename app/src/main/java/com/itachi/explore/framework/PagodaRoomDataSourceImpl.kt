package com.itachi.explore.framework

import android.util.Log
import com.itachi.core.common.Resource
import com.itachi.core.data.db.PagodaRoomDataSource
import com.itachi.core.domain.PagodaVO
import com.itachi.explore.framework.mappers.PagodaMapper
import com.itachi.explore.persistence.MyDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.koin.core.KoinComponent
import org.koin.core.inject

class PagodaRoomDataSourceImpl(
    private val pagodaMapper: PagodaMapper,
    private val database: MyDatabase
) : PagodaRoomDataSource, KoinComponent {

    override suspend fun addPagoda(pagodaVO: PagodaVO) {
        Log.d("test---", "add pagoda")
        database.pagodaDao().addPagoda(pagodaMapper.voToEntity(pagodaVO))
    }

    override suspend fun deletePagoda(pagodaVO: PagodaVO) {
        database.pagodaDao().deletePagodaById(pagodaVO.item_id)
    }

    override fun getPagodaById(id: String): Flow<PagodaVO> =
        database.pagodaDao().getPagodaById(id)
            .map {
                pagodaMapper.entityToVO(it)
            }

    override fun getAllPagodas(): Flow<List<PagodaVO>> =
        database.pagodaDao().getPagodaList()
            .map {
                pagodaMapper.entityListToVOList(it)
            }

    override suspend fun updatePagoda(pagodaVO: PagodaVO) {
        database.pagodaDao().addPagoda(pagodaMapper.voToEntity(pagodaVO))
    }

    override suspend fun addAllPagodas(pagodaList: List<PagodaVO>) {
        database.pagodaDao().insertPagodaList(pagodaMapper.voListToEntityList(pagodaList))
    }

    override suspend fun deleteAllPagodas() {
        database.pagodaDao().deletePagodaList()
    }


}