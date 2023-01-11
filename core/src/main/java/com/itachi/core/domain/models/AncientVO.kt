package com.itachi.core.domain.models

import java.io.Serializable

data class AncientVO(

    override var about: String = "",

    override var comments: List<String> = emptyList(),

    override var created_date: String = "",

    override var festival_date: String = "",

    override var is_there_festival: Boolean = false,

    override var item_id: String = "",

    override var item_type: String = "",

    override var photos: List<PhotoVO> = emptyList(),

    override var title: String = "",

    override var uploader_id: String = ""

) : Serializable, ItemVO(false)