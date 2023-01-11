package com.itachi.core.domain.models

import java.io.Serializable

data class UploadedPhotoVO (
    var url : String = "",
    var uploader_id : String = "",
    var item_id : String = "",
    var item_type : String = "",
    var geo_points : String = ""
): Serializable