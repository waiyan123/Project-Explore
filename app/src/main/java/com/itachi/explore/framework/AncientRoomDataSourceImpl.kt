package com.itachi.explore.framework

import com.itachi.core.data.db.AncientRoomDataSource
import com.itachi.core.domain.AncientVO
import com.itachi.explore.framework.mappers.AncientMapper
import com.itachi.explore.framework.mappers.ListMapperImpl
import com.itachi.explore.framework.mappers.Mapper
import com.itachi.explore.persistence.MyDatabase
import com.itachi.explore.persistence.entities.AncientEntity
import org.koin.core.KoinComponent
import org.koin.core.inject

class AncientRoomDataSourceImpl(private val ancientMapper: AncientMapper) : AncientRoomDataSource, KoinComponent {

    private val database: MyDatabase by inject()

    override suspend fun add(ancientVO: AncientVO) {
        database.ancientDao().addAncient(ancientMapper.voToEntity(ancientVO))
    }

    override suspend fun addAll(ancientList: List<AncientVO>) {
        database.ancientDao().insertAncientList(ancientMapper.voListToEntityList(ancientList))
    }

    override suspend fun delete(ancientVO: AncientVO) {
        database.ancientDao().deleteAncientById(ancientVO.item_id)
    }

    override suspend fun deleteAll(ancientList: List<AncientVO>) {
        database.ancientDao().deleteAncientList()
    }

    override suspend fun get(id: String) = ancientMapper.entityToVO(database.ancientDao().getAncientById(id))

    override suspend fun getAll(): List<AncientVO> = ancientMapper.entityListToVOList(database.ancientDao().getAncientsList())

    override suspend fun update(ancientVO: AncientVO) {
        TODO("Not yet implemented")
    }
}