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
import org.koin.core.KoinComponent
import org.koin.core.inject

class PagodaRoomDataSourceImpl(
    private val pagodaMapper: PagodaMapper
) : PagodaRoomDataSource, KoinComponent {

    private val database: MyDatabase by inject()

    override suspend fun addPagoda(pagodaVO: PagodaVO) {
        Log.d("test---","add pagoda to room")
        database.pagodaDao().addPagoda(pagodaMapper.voToEntity(pagodaVO))
    }

    override suspend fun deletePagoda(pagodaVO: PagodaVO) {
        Log.d("test---","delete pagoda from room")
        database.pagodaDao().deletePagodaById(pagodaVO.item_id)
    }

    override fun getPagodaById(id: String) : Flow<PagodaVO> = flow{
        database.pagodaDao().getPagodaById(id)
            .collect {
                emit(pagodaMapper.entityToVO(it))
            }

    }

    override fun getAllPagodas() : Flow<List<PagodaVO>> = flow {
        database.pagodaDao().getPagodaList()
            .collect {
                emit(pagodaMapper.entityListToVOList(it))
            }
    }

    override suspend fun updatePagoda(pagodaVO: PagodaVO) {
        database.pagodaDao().addPagoda(pagodaMapper.voToEntity(pagodaVO))
    }

    override suspend fun addAllPagodas(pagodaList: List<PagodaVO>) {
        Log.d("test---","add all pagoda to room")
        database.pagodaDao().insertPagodaList(pagodaMapper.voListToEntityList(pagodaList))
    }

    override suspend fun deleteAllPagodas() {
        Log.d("test---","delete all pagoda from room")
        database.pagodaDao().deletePagodaList()
    }




}