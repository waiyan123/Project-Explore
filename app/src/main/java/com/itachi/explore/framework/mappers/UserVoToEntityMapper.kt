package com.itachi.explore.framework.mappers

import com.itachi.core.domain.UserVO
import com.itachi.core.domain.ViewVO
import com.itachi.explore.persistence.entities.PhotoEntity
import com.itachi.explore.persistence.entities.UserEntity
import com.itachi.explore.persistence.entities.ViewEntity

class UserVoToEntityMapper : Mapper<UserVO, UserEntity>{

    override fun map(input: UserVO?) : UserEntity {
        if(input!=null) {
            val profileEntity = PhotoEntity(input.profile_pic.path,input.profile_pic.url,input.profile_pic.geo_points)
            val bgEntity = PhotoEntity(input.background_pic.path,input.background_pic.url,input.background_pic.geo_points)
            return UserEntity(
                input.facebook_id,
                input.user_id,
                input.phone_number,
                input.facebook_profile_url,
                input.email,
                input.name,
                profileEntity,
                bgEntity,
                input.brief_bio,
                input.get_is_uploader
            )
        }
        else return UserEntity()
    }
}