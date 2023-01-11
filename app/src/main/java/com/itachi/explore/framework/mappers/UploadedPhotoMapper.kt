package com.itachi.explore.framework.mappers

import com.itachi.core.domain.models.UploadedPhotoVO
import com.itachi.explore.persistence.entities.UploadedPhotoEntity

class UploadedPhotoMapper(
    private val uploadedPhotoEntityToVoMapper : Mapper<UploadedPhotoEntity, UploadedPhotoVO>,
    private val uploadedPhotoVoToEntityMapper : Mapper<UploadedPhotoVO,UploadedPhotoEntity>,
    private val uploadedPhotoVoToFirebaseMapper : Mapper<UploadedPhotoVO,HashMap<String,Any>>
) : UploadedPhotoMapperFunctions{

    override fun entityToVO(uploadedPhotoEntity: UploadedPhotoEntity) = uploadedPhotoEntityToVoMapper.map(uploadedPhotoEntity)

    override fun voToEntity(uploadedPhotoVO: UploadedPhotoVO) = uploadedPhotoVoToEntityMapper.map(uploadedPhotoVO)

    override fun voToFirebaseHashmap(uploadedPhotoVO: UploadedPhotoVO) = uploadedPhotoVoToFirebaseMapper.map(uploadedPhotoVO)
}