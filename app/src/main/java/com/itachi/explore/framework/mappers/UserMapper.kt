package com.itachi.explore.framework.mappers

import com.itachi.core.domain.AncientVO
import com.itachi.core.domain.PhotoVO
import com.itachi.core.domain.UserVO
import com.itachi.explore.persistence.entities.AncientEntity
import com.itachi.explore.persistence.entities.UserEntity

class UserMapper(private val photoEntityToVoMapper : PhotoEntityToVoMapper) : Mapper<UserEntity, UserVO> {

    override fun map(input: UserEntity?): UserVO {
        if(input!=null) {
            return UserVO(
                input.facebook_id,
                input.user_id ?: "",
                input.phone_number ?: "",
                input.facebook_profile_url ?: "",
                input.email ?: "",
                input.name ?: "",
                photoEntityToVoMapper.map(input.profile_pic),
                photoEntityToVoMapper.map(input.background_pic),
                input.brief_bio ?: "",
                input.get_is_uploader ?: false
            )
        }
        else return UserVO("","","","",
            "","",PhotoVO("","",""),PhotoVO("","",""),"",false)
    }
}