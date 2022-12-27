package com.itachi.explore.framework.mappers

import com.itachi.core.domain.PagodaVO
import com.itachi.explore.persistence.entities.PagodaEntity

class PagodaMapper(
    private val pagodaEntityToVoMapper: PagodaEntityToVoMapper,
    private val pagodaVoToEntityMapper: PagodaVoToEntityMapper,
    private val pagodaVoToFirebaseMapper: PagodaVoToFirebaseMapper,
    private val pagodaEntityListToVOListMapper : ListMapperImpl<PagodaEntity, PagodaVO>,
    private val pagodaVoListToEntityListMapper : ListMapperImpl<PagodaVO, PagodaEntity>
) {
    fun entityToVO(pagodaEntity: PagodaEntity?) = pagodaEntityToVoMapper.map(pagodaEntity)
    fun voToEntity(pagodaVO : PagodaVO?) = pagodaVoToEntityMapper.map(pagodaVO)
    fun voToFirebaseHashmap(pagodaVO: PagodaVO?) = pagodaVoToFirebaseMapper.map(pagodaVO)
    fun entityListToVOList(pagodaEntities : List<PagodaEntity>?) = pagodaEntityListToVOListMapper.map(pagodaEntities)
    fun voListToEntityList(pagodaVoList : List<PagodaVO>?) = pagodaVoListToEntityListMapper.map(pagodaVoList)
}