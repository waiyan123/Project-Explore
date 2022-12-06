package com.itachi.explore.persistence.entities

import androidx.room.ColumnInfo
import java.io.Serializable

data class PhotoEntity (
    var id : String = "123456789",
    var url : String = "",
    var geo_points : String = ""
): Serializable