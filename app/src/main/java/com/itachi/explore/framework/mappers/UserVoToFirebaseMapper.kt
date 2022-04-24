package com.itachi.explore.framework.mappers

import com.itachi.core.domain.UserVO
import com.itachi.core.domain.ViewVO
import com.itachi.explore.persistence.entities.PhotoEntity
import com.itachi.explore.utils.*

class UserVoToFirebaseMapper : Mapper<UserVO,HashMap<String,Any>> {

    override fun map(input: UserVO?): HashMap<String, Any> {

        return if(input!=null) {
            hashMapOf(
                FACEBOOK_ID to "",
                USER_ID to input.user_id,
                USER_PHONE_NUMBER to input.phone_number,
                USER_FACEBOOK_URL to "",
                USER_EMAIL to input.email,
                USER_NAME to input.name,
                USER_PROFILE_PIC to PhotoEntity("","",""),
                USER_BACKGROUND_PIC to PhotoEntity("","",""),
                USER_BRIEF_BIO to "",
                USER_IS_UPLOADER to false
            )
        } else hashMapOf()
    }
}