package com.itachi.explore.framework.mappers

import com.itachi.core.domain.AncientVO
import com.itachi.core.domain.PhotoVO
import com.itachi.core.domain.UserVO
import com.itachi.explore.persistence.entities.PhotoEntity
import com.itachi.explore.persistence.entities.UserEntity

class PhotoEntityToVoMapper : Mapper<PhotoEntity, PhotoVO> {

    override fun map(input: PhotoEntity?): PhotoVO {
        if(input!=null) {
            return PhotoVO(
                input.path ?: "",
                input.url ?: "",
                input.geo_points ?: ""
            )
        }
        else return PhotoVO("","","")
    }
}