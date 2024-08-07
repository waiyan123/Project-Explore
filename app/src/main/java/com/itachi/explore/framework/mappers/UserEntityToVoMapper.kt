package com.itachi.explore.framework.mappers

import com.itachi.core.domain.models.PhotoVO
import com.itachi.core.domain.models.UserVO
import com.itachi.explore.persistence.entities.UserEntity

class UserEntityToVoMapper : Mapper<UserEntity, UserVO> {

    override fun map(input: UserEntity?): UserVO {
        if(input!=null) {
            val profileEntity = PhotoVO(input.profile_pic!!.id,input.profile_pic!!.url,
                input.profile_pic!!.geo_points)
            val bgEntity = PhotoVO(input.background_pic!!.id,input.background_pic!!.url,
                input.background_pic!!.geo_points)
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