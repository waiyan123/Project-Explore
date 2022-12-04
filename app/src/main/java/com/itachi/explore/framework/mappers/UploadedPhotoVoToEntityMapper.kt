package com.itachi.explore.framework.mappers

import com.itachi.core.domain.PhotoVO
import com.itachi.core.domain.UploadedPhotoVO
import com.itachi.explore.persistence.entities.PhotoEntity
import com.itachi.explore.persistence.entities.UploadedPhotoEntity

class UploadedPhotoVoToEntityMapper : Mapper<UploadedPhotoVO, UploadedPhotoEntity> {

    override fun map(input: UploadedPhotoVO?): UploadedPhotoEntity {
        return if (input != null) {
            UploadedPhotoEntity(
                input.url,
                input.uploader_id,
                input.item_id,
                input.item_type,
                input.geo_points
            )
        } else UploadedPhotoEntity()
    }
}