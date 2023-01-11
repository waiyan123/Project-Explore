package com.itachi.explore.framework.mappers

import com.itachi.core.domain.models.UploadedPhotoVO
import com.itachi.explore.persistence.entities.UploadedPhotoEntity

interface UploadedPhotoMapperFunctions {
    fun entityToVO(uploadedPhotoEntity: UploadedPhotoEntity) : UploadedPhotoVO
    fun voToEntity(uploadedPhotoVO: UploadedPhotoVO) : UploadedPhotoEntity
    fun voToFirebaseHashmap(uploadedPhotoVO: UploadedPhotoVO) : HashMap<String,Any>
}