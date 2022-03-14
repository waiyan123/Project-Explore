package com.itachi.explore.framework.mappers

import com.itachi.core.domain.AncientVO
import com.itachi.core.domain.PhotoVO
import com.itachi.explore.persistence.entities.AncientEntity

open class AncientEntityToVoMapper : Mapper<AncientEntity,AncientVO> {

    override fun map(input: AncientEntity?): AncientVO {
        if(input!=null) {
            val photoEntity = input.photos?.map {
                PhotoVO(it.path!!,it.url!!,it.geo_points!!)
            }
            return AncientVO(
                input.about ?: "",
                input.comments ?: emptyList(),
                input.created_date ?: "",
                input.festival_date ?: "",
                input.is_there_festival ?: false,
                input.item_id ?: "",
                input.item_type ?: "",
                photoEntity?: emptyList(),
                input.title ?: "",
                input.uploader_id ?: ""
            )
        }
        else return AncientVO("", emptyList(),"","",false,"","",
        emptyList(),"","")
    }
}