package com.itachi.explore.framework.mappers

import com.itachi.core.domain.ViewVO
import com.itachi.explore.persistence.entities.PhotoEntity
import com.itachi.explore.persistence.entities.ViewEntity

class ViewVoToEntityMapper : Mapper<ViewVO, ViewEntity>{

    override fun map(input: ViewVO?) : ViewEntity {
        if(input!=null) {
            val photoEntity = input.photos.map {
                PhotoEntity(it.id,it.url,it.geo_points)
            }
            return ViewEntity(
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
        else return ViewEntity()
    }
}