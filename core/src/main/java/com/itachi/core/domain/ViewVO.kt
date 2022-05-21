package com.itachi.core.domain

import java.io.Serializable

data class ViewVO(
    override var about: String,

    override var comments: List<String>,

    override var created_date: String,

    override var festival_date: String,

    override var is_there_festival: Boolean,

    override var item_id: String,

    override var item_type: String,

    override var photos: List<PhotoVO>,

    override var title: String,

    override var uploader_id: String

) : Serializable,ItemVO(false)