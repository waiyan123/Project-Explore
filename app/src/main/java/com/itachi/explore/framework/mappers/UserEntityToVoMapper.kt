package com.itachi.explore.framework.mappers

import com.itachi.core.domain.PhotoVO
import com.itachi.core.domain.UserVO
import com.itachi.core.domain.ViewVO
import com.itachi.explore.persistence.entities.PhotoEntity
import com.itachi.explore.persistence.entities.UserEntity
import com.itachi.explore.persistence.entities.ViewEntity

class UserEntityToVoMapper : Mapper<UserEntity, UserVO> {

    override fun map(input: UserEntity?): UserVO {
        if(input!=null) {
            val profileEntity = PhotoVO(input.profile_pic!!.path!!,input.profile_pic!!.url!!,
                input.profile_pic!!.geo_points!!)
            val bgEntity = PhotoVO(input.background_pic!!.path!!,input.background_pic!!.url!!,
                input.background_pic!!.geo_points!!)
            return UserVO(
                input.facebook_id ?: "",
                input.user_id ?: "",
                input.phone_number ?: "",
                input.facebook_profile_url ?: "",
                input.email ?: "",
                input.name ?: "",
                profileEntity ,
                bgEntity,
                input.brief_bio ?: "",
                input.get_is_uploader ?: false
            )
        }
        else return UserVO("", "","","","","",
            PhotoVO("","",""), PhotoVO("","",""),"",false)
    }
}