package com.itachi.core.domain

import java.io.Serializable


data class UserVO(

    var facebook_id: String,

    val user_id: String,

    var phone_number : String,

    var facebook_profile_url : String,

    var email: String,

    var name: String,

    var profile_pic : PhotoVO,

    var background_pic : PhotoVO,

    var brief_bio: String,

    var get_is_uploader : Boolean

) : Serializable