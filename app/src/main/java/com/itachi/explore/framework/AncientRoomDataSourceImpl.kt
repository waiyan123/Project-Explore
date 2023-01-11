package com.itachi.explore.framework

import com.itachi.core.data.room.AncientRoomDataSource
import com.itachi.core.domain.models.AncientVO
import com.itachi.explore.framework.mappers.AncientMapper
import com.itachi.explore.persistence.MyDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

class AncientRoomDataSourceImpl(
    private val ancientMapper: AncientMapper,
    private val database: MyDatabase
) : AncientRoomDataSource {

    override suspend fun addAncient(ancientVO: AncientVO) {
        withContext(Dispatchers.IO) {
            database.ancientDao().addAncient(ancientMapper.voToEntity(ancientVO))
        }
    }

    override suspend fun addAllAncients(ancientList: List<AncientVO>) {
        withContext(Dispatchers.IO) {
            database.ancientDao().insertAncientList(ancientMapper.voListToEntityList(ancientList))
        }
    }

    override suspend fun deleteAncient(ancientVO: AncientVO) {
        withContext(Dispatchers.IO) {
            database.ancientDao().deleteAncientById(ancientVO.item_id)
        }
    }

    override suspend fun deleteAllAncients() {
        withContext(Dispatchers.IO) {
            database.ancientDao().deleteAncientList()
        }

    }

    override fun getAncientById(id: String): Flow<AncientVO> =
        database.ancientDao().getAncientById(id)
            .map {
                ancientMapper.entityToVO(it)
            }
            .flowOn(Dispatchers.IO)

    override fun getAllAncients(): Flow<List<AncientVO>> =
        database.ancientDao().getAncientsList()
            .map {
                ancientMapper.entityListToVOList(it)
            }
            .flowOn(Dispatchers.IO)

    override suspend fun update(ancientVO: AncientVO) {
        withContext(Dispatchers.IO) {
            database.ancientDao().updateAncient(ancientMapper.voToEntity(ancientVO))
        }
    }

}