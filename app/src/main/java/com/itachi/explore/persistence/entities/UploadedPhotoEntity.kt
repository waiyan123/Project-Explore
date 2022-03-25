package com.itachi.explore.persistence.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import java.io.Serializable

@Entity(tableName = "uploaded_photo_table")
data class UploadedPhotoEntity (

    @ColumnInfo(name = "url")
    var url : String? = "",

    @ColumnInfo(name = "uploaded_id")
    var uploader_id : String? = "",

    @ColumnInfo(name = "item_id")
    var item_id : String? = "",

    @ColumnInfo(name = "item_type")
    var item_type : String? = "",

    @ColumnInfo(name = "geo_points")
    var geo_points : String? = ""
): Serializable