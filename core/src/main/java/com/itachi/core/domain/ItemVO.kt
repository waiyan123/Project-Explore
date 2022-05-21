package com.itachi.core.domain

import java.io.Serializable

abstract class ItemVO(
    var editable : Boolean
) : Serializable {
    abstract var about: String

    abstract var comments: List<String>

    abstract var created_date: String

    abstract var festival_date: String

    abstract var is_there_festival: Boolean

    abstract var item_id: String

    abstract var item_type: String

    abstract var photos: List<PhotoVO>

    abstract var title: String

    abstract var uploader_id: String

}