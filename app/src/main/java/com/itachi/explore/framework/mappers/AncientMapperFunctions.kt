package com.itachi.explore.framework.mappers

import com.itachi.core.domain.AncientVO
import com.itachi.explore.persistence.entities.AncientEntity

interface AncientMapperFunctions {
    fun entityToVO(ancientEntity: AncientEntity?) : AncientVO
    fun voToEntity(ancientVO: AncientVO?) : AncientEntity
    fun voToFirebaseHashmap(ancientVO: AncientVO?) : HashMap<String,Any>
    fun entityListToVOList(ancientEntities : List<AncientEntity>?) : List<AncientVO>
    fun voListToEntityList(ancientVoList : List<AncientVO>?) : List<AncientEntity>
}