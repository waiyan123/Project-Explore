package com.itachi.explore.framework.mappers

import android.util.Log
import com.itachi.core.domain.UserVO
import com.itachi.core.domain.ViewVO
import com.itachi.explore.persistence.entities.PhotoEntity
import com.itachi.explore.utils.*

class UserVoToFirebaseMapper : Mapper<UserVO,HashMap<String,Any>> {

    override fun map(input: UserVO?): HashMap<String, Any> {

        return if(input!=null) {
            hashMapOf(
                FACEBOOK_ID to input.facebook_id,
                USER_ID to input.user_id,
                USER_PHONE_NUMBER to input.phone_number,
                USER_FACEBOOK_URL to input.facebook_profile_url,
                USER_EMAIL to input.email,
                USER_NAME to input.name,
                USER_PROFILE_PIC to input.profile_pic,
                USER_BACKGROUND_PIC to input.background_pic,
                USER_BRIEF_BIO to input.brief_bio,
                USER_IS_UPLOADER to input.get_is_uploader
            )
        } else hashMapOf()
    }
}