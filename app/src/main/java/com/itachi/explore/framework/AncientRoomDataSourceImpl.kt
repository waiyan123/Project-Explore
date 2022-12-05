package com.itachi.explore.framework

import android.util.Log
import com.itachi.core.data.db.AncientRoomDataSource
import com.itachi.core.domain.AncientVO
import com.itachi.explore.framework.mappers.AncientMapper
import com.itachi.explore.framework.mappers.ListMapperImpl
import com.itachi.explore.framework.mappers.Mapper
import com.itachi.explore.persistence.MyDatabase
import com.itachi.explore.persistence.entities.AncientEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import org.koin.core.KoinComponent
import org.koin.core.inject

class AncientRoomDataSourceImpl(
    private val ancientMapper: AncientMapper,
    private val database: MyDatabase
) : AncientRoomDataSource {

    override suspend fun addAncient(ancientVO: AncientVO) {
        Log.d("test---","add ancient to room")
        database.ancientDao().addAncient(ancientMapper.voToEntity(ancientVO))
    }

    override suspend fun addAllAncients(ancientList: List<AncientVO>) {
        Log.d("test---","add all ancient to room")
        database.ancientDao().insertAncientList(ancientMapper.voListToEntityList(ancientList))
    }

    override suspend fun deleteAncient(ancientVO: AncientVO) {
        Log.d("test---","delete ancient from room")
        database.ancientDao().deleteAncientById(ancientVO.item_id)
    }

    override suspend fun deleteAllAncients() {
        Log.d("test---","delete all ancients from room")
        database.ancientDao().deleteAncientList()
    }

    override fun getAncientById(id: String): Flow<AncientVO> = flow {
        database.ancientDao().getAncientById(id)
            .collect {
                emit(ancientMapper.entityToVO(it))
            }
    }

    override fun getAllAncients(): Flow<List<AncientVO>> = flow {
        database.ancientDao().getAncientsList()
            .collect {
                emit(ancientMapper.entityListToVOList(it))
            }
    }

    override suspend fun update(ancientVO: AncientVO) {
        database.ancientDao().updateAncient(ancientMapper.voToEntity(ancientVO))
    }

}