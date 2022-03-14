package com.itachi.explore.persistence.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "user_table")
data class UserEntity(

    @PrimaryKey
    @ColumnInfo(name="facebook_id")
    var facebook_id: String= "",

    @ColumnInfo(name="user_id")
    val user_id: String?= "",

    @ColumnInfo(name="phone_number")
    var phone_number : String?= "",

    @ColumnInfo(name="facebook_profile_url")
    var facebook_profile_url : String?= "",

    @ColumnInfo(name="email")
    var email: String?= "",

    @ColumnInfo(name="name")
    var name: String?= "",

    @ColumnInfo(name="profile_pic")
//    var profile_pic: String?= "",
    var profile_pic : PhotoEntity? = null,

    @ColumnInfo(name="background_pic")
//    var background_pic : String?= "",
    var background_pic : PhotoEntity? = null,

    @ColumnInfo(name="brief_bio")
    var brief_bio: String?= "",

    @ColumnInfo(name="get_is_uploader")
    var get_is_uploader : Boolean?= false

) : Serializable