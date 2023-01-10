package com.itachi.explore.framework.mappers

import com.itachi.core.domain.PagodaVO
import com.itachi.core.domain.UploadedPhotoVO
import com.itachi.core.domain.ViewVO
import com.itachi.explore.persistence.entities.PagodaEntity
import com.itachi.explore.persistence.entities.UploadedPhotoEntity
import com.itachi.explore.persistence.entities.ViewEntity
import javax.inject.Inject

class ViewMapper {
    private val viewEntityToVoMapper = ViewEntityToVoMapper()
    private val viewVoToEntityMapper = ViewVoToEntityMapper()
    private val viewVoToFirebaseMapper = ViewVoToFirebaseMapper()
    private val uploadedPhotoEntityToVoMapper = UploadedPhotoEntityToVoMapper()
    private val viewEntityListToVOListMapper = ListMapperImpl<ViewEntity, ViewVO>(viewEntityToVoMapper)
    private val viewVoListToEntityListMapper = ListMapperImpl<ViewVO, ViewEntity>(viewVoToEntityMapper)
    private val photoEntityListToVoMapper = ListMapperImpl<UploadedPhotoEntity, UploadedPhotoVO>(uploadedPhotoEntityToVoMapper)

    fun uploadedPhotoEntityToVoList(uploadedPhotoEntities: List<UploadedPhotoEntity>?) = photoEntityListToVoMapper.map(uploadedPhotoEntities)

    fun entityToVO(viewEntity: ViewEntity?) = viewEntityToVoMapper.map(viewEntity)
    fun voToEntity(viewVO: ViewVO?) = viewVoToEntityMapper.map(viewVO)
    fun voToFirebaseHashmap(viewVO: ViewVO?) = viewVoToFirebaseMapper.map(viewVO)
    fun entityListToVOList(viewEntities: List<ViewEntity>?) = viewEntityListToVOListMapper.map(viewEntities)
    fun voListToEntityList(viewVoList: List<ViewVO>?) = viewVoListToEntityListMapper.map(viewVoList)
}