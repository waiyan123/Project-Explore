package com.itachi.explore.framework.mappers

import com.itachi.core.domain.UserVO
import com.itachi.explore.persistence.entities.PhotoEntity
import com.itachi.explore.persistence.entities.UserEntity

class UserVoToEntityMapper : Mapper<UserVO, UserEntity>{

    override fun map(input: UserVO?) : UserEntity {
        if(input!=null) {
            val profileEntity = PhotoEntity(input.profile_pic.id,input.profile_pic.url,input.profile_pic.geo_points)
            val bgEntity = PhotoEntity(input.background_pic.id,input.background_pic.url,input.background_pic.geo_points)
            return UserEntity(
                user_id = input.user_id,
                facebook_id = input.facebook_id,
                phone_number = input.phone_number,
                facebook_profile_url = input.facebook_profile_url,
                email = input.email,
                name = input.name,
                profile_pic = profileEntity,
                background_pic = bgEntity,
                brief_bio = input.brief_bio,
                get_is_uploader = input.get_is_uploader
            )
        }
        else return UserEntity()
    }
}