package com.itachi.explore.framework.mappers

import com.itachi.core.domain.AncientVO
import com.itachi.explore.persistence.entities.AncientEntity

class AncientMapper(
    private val ancientEntityToVoMapper: AncientEntityToVoMapper,
    private val ancientVoToEntityMapper: AncientVoToEntityMapper,
    private val ancientVoToFirebaseMapper: AncientVoToFirebaseMapper,
    private val ancientEntityListToVOListMapper : ListMapperImpl<AncientEntity,AncientVO>,
    private val ancientVoListToEntityListMapper : ListMapperImpl<AncientVO,AncientEntity>
) {
    fun entityToVO(ancientEntity: AncientEntity) = ancientEntityToVoMapper.map(ancientEntity)
    fun voToEntity(ancientVO: AncientVO) = ancientVoToEntityMapper.map(ancientVO)
    fun voToFirebaseHashmap(ancientVO: AncientVO) = ancientVoToFirebaseMapper.map(ancientVO)
    fun entityListToVOList(ancientEntities : List<AncientEntity>) = ancientEntityListToVOListMapper.map(ancientEntities)
    fun voListToEntityList(ancientVoList : List<AncientVO>) = ancientVoListToEntityListMapper.map(ancientVoList)
}