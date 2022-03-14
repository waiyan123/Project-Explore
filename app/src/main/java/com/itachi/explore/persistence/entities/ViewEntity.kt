package com.itachi.explore.persistence.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "view_table")
data class ViewEntity(
    @ColumnInfo(name = "about")
    var about: String?= "",

    @ColumnInfo(name = "comments")
    var comments: List<String>?= null,

    @ColumnInfo(name = "created_date")
    var created_date: String?= "",

    @ColumnInfo(name = "festival_date")
    var festival_date: String?= "",

    @ColumnInfo(name = "is_there_festival")
    var is_there_festival: Boolean? = false,

    @PrimaryKey
    @ColumnInfo(name = "item_id")
    var item_id: String = "",

    @ColumnInfo(name = "item_type")
    var item_type: String? = "",

    @ColumnInfo(name = "photos")
    var photos: List<PhotoEntity>? = null,

    @ColumnInfo(name = "title")
    var title: String? = "",

    @ColumnInfo(name = "uploader_id")
    var uploader_id: String? = ""

) : Serializable