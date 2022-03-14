package com.itachi.explore.persistence.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CommentTypeConverter {

    @TypeConverter
    fun toString(list : List<String>?) :String {

        return Gson().toJson(list)
    }

    @TypeConverter
    fun toList(json:String) : List<String>?{

        val typeToken = object : TypeToken<List<String>>(){

        }.type
        return Gson().fromJson(json,typeToken)
    }
}