package com.itachi.explore.persistence.entities

import androidx.room.ColumnInfo
import java.io.Serializable

data class UploadedPhotoEntity (
    var url : String? = "",
    var uploader_id : String? = "",
    var item_id : String? = "",
    var item_type : String? = "",
    var geo_points : String? = ""
): Serializable