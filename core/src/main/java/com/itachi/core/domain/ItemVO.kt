package com.itachi.core.domain

import java.io.Serializable

data class ItemVO(
    var about: String,

    var comments: List<String>,

    var created_date: String,

    var festival_date: String,

    var is_there_festival: Boolean,

    var item_id: String,

    var item_type: String,

    var photos: List<PhotoVO>,

    var title: String,

    var uploader_id: String,

    var editable : Boolean

) : Serializable