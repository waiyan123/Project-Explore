package com.itachi.explore.framework.mappers

import com.itachi.core.domain.models.PhotoVO
import com.itachi.core.domain.models.ViewVO
import com.itachi.explore.persistence.entities.ViewEntity

class ViewEntityToVoMapper : Mapper<ViewEntity, ViewVO> {

    override fun map(input: ViewEntity?): ViewVO {
        if(input!=null) {
            val viewEntity = input.photos?.map {
                PhotoVO(it.id,it.url,it.geo_points)
            }
            return ViewVO(
                input.about ?: "",
                input.comments ?: emptyList(),
                input.created_date ?: "",
                input.festival_date ?: "",
                input.is_there_festival ?: false,
                input.item_id ?: "",
                input.item_type ?: "",
                viewEntity?: emptyList(),
                input.title ?: "",
                input.uploader_id ?: ""
            )
        }
        else return ViewVO("", emptyList(),"","",false,"","",
            emptyList(),"","")
    }
}