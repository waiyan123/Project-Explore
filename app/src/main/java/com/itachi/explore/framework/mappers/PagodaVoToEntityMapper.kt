package com.itachi.explore.framework.mappers

import com.itachi.core.domain.PagodaVO
import com.itachi.explore.persistence.entities.PagodaEntity
import com.itachi.explore.persistence.entities.PhotoEntity
import javax.inject.Inject

class PagodaVoToEntityMapper @Inject constructor() : Mapper<PagodaVO, PagodaEntity>{

    override fun map(input: PagodaVO?) : PagodaEntity {
        if(input!=null) {
            val photoEntity = input.photos.map {
                PhotoEntity(it.id,it.url,it.geo_points)
            }
            return PagodaEntity(
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
        else return PagodaEntity()
    }
}