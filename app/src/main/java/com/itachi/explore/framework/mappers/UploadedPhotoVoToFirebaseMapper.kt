package com.itachi.explore.framework.mappers

import com.itachi.core.domain.models.UploadedPhotoVO
import com.itachi.explore.utils.*

open class UploadedPhotoVoToFirebaseMapper : Mapper<UploadedPhotoVO,HashMap<String,Any>>{
    override fun map(input: UploadedPhotoVO?): HashMap<String, Any> {
        return if(input!=null) {
            hashMapOf(
                PHOTO_URL to input.url,
                UPLOADER_ID to input.uploader_id,
                ITEM_ID to input.item_id,
                ITEM_TYPE to input.item_type,
                GEO_POINTS to input.geo_points
            )
        } else hashMapOf()
    }


}