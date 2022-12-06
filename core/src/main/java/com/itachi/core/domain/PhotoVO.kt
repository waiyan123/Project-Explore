package com.itachi.core.domain

import java.io.Serializable

data class PhotoVO (
    var id : String = "123456789",
    var url : String = "",
    var geo_points : String = ""
): Serializable