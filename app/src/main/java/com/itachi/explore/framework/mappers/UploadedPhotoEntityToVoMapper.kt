package com.itachi.explore.framework.mappers

import com.itachi.core.domain.PhotoVO
import com.itachi.core.domain.UploadedPhotoVO
import com.itachi.core.domain.ViewVO
import com.itachi.explore.persistence.entities.UploadedPhotoEntity
import com.itachi.explore.persistence.entities.ViewEntity
import javax.inject.Inject

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