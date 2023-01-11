package com.itachi.explore.framework.mappers

import com.itachi.core.domain.models.UploadedPhotoVO
import com.itachi.explore.persistence.entities.UploadedPhotoEntity

open class UploadedPhotoEntityToVoMapper : Mapper<UploadedPhotoEntity, UploadedPhotoVO> {

    override fun map(input: UploadedPhotoEntity?): UploadedPhotoVO {
        return if(input!=null) {

            UploadedPhotoVO(
                input.url ?: "",
                input.uploader_id ?: "",
                input.item_id ?: "",
                input.item_type ?: "",
                input.geo_points ?: ""
            )
        } else UploadedPhotoVO("","","","","")
    }
}