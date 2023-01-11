package com.itachi.core.domain.models

import java.io.Serializable


data class UserVO(

    var facebook_id: String="",

    val user_id: String="",

    var phone_number : String="",

    var facebook_profile_url : String="",

    var email: String="",

    var name: String="",

    var profile_pic : PhotoVO = PhotoVO("","",""),

    var background_pic : PhotoVO = PhotoVO("","",""),

    var brief_bio: String="",

    var get_is_uploader : Boolean =false

) : Serializable