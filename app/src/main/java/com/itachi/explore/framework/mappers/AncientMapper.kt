package com.itachi.explore.framework.mappers

import com.itachi.core.domain.models.AncientVO
import com.itachi.explore.persistence.entities.AncientEntity

class AncientMapper(
    private val ancientEntityToVoMapper: Mapper<AncientEntity, AncientVO>,
    private val ancientVoToEntityMapper: Mapper<AncientVO, AncientEntity>,
    private val ancientVoToFirebaseMapper: Mapper<AncientVO, HashMap<String, Any>>,
    private val ancientEntityListToVOListMapper: ListMapper<AncientEntity, AncientVO>,
    private val ancientVoListToEntityListMapper: ListMapper<AncientVO, AncientEntity>
) : AncientMapperFunctions {

    override fun entityToVO(ancientEntity: AncientEntity?) = ancientEntityToVoMapper.map(ancientEntity)
    override fun voToEntity(ancientVO: AncientVO?) = ancientVoToEntityMapper.map(ancientVO)
    override fun voToFirebaseHashmap(ancientVO: AncientVO?) = ancientVoToFirebaseMapper.map(ancientVO)
    override fun entityListToVOList(ancientEntities: List<AncientEntity>?) = ancientEntityListToVOListMapper.map(ancientEntities)
    override fun voListToEntityList(ancientVoList: List<AncientVO>?) = ancientVoListToEntityListMapper.map(ancientVoList)
}