package com.itachi.explore.framework.mappers

import com.itachi.core.domain.PagodaVO
import com.itachi.core.domain.PhotoVO
import com.itachi.explore.persistence.entities.PagodaEntity

class PagodaEntityToVoMapper : Mapper<PagodaEntity, PagodaVO> {

    override fun map(input: PagodaEntity?): PagodaVO {
        if(input!=null) {
            val photoEntity = input.photos?.map {
                PhotoVO(it.path!!,it.url!!,it.geo_points!!)
            }
            return PagodaVO(
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
        else return PagodaVO("", emptyList(),"","",false,"","",
            emptyList(),"","")
    }
}