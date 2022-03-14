package com.itachi.explore.framework.mappers

import com.itachi.core.domain.AncientVO
import com.itachi.core.domain.PhotoVO
import com.itachi.explore.persistence.entities.AncientEntity
import com.itachi.explore.persistence.entities.PhotoEntity

open class AncientVoToEntityMapper : Mapper<AncientVO,AncientEntity>{

    override fun map(input: AncientVO?) : AncientEntity {
        if(input!=null) {
            val photoEntity = input.photos.map {
                PhotoEntity(it.path,it.url,it.geo_points)
            }
            return AncientEntity(
                input.about,
                input.comments,
                input.created_date,
                input.festival_date,
                input.is_there_festival,
                input.item_id,
                input.item_type,
                photoEntity,
                input.title,
                input.uploader_id
            )
        }
        else return AncientEntity()
    }
}