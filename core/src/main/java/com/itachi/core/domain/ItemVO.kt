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

fun ItemVO.toPagodaVO() : PagodaVO {
    return PagodaVO(
        about,
        comments,
        created_date,
        festival_date,
        is_there_festival,
        item_id,
        item_type,
        photos,
        title,
        uploader_id
    )
}

fun ItemVO.toViewVO() : ViewVO {
    return ViewVO(
        about,
        comments,
        created_date,
        festival_date,
        is_there_festival,
        item_id,
        item_type,
        photos,
        title,
        uploader_id
    )
}

fun ItemVO.toAncientVO() : AncientVO {
    return AncientVO(
        about,
        comments,
        created_date,
        festival_date,
        is_there_festival,
        item_id,
        item_type,
        photos,
        title,
        uploader_id
    )
}