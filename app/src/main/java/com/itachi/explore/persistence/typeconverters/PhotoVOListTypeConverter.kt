package com.itachi.explore.persistence.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.itachi.explore.persistence.entities.PhotoEntity

class PhotoVOListTypeConverter {

    @TypeConverter
    fun toString(list : List<PhotoEntity>) :String {

        return Gson().toJson(list)
    }

    @TypeConverter
    fun toList(json:String) : List<PhotoEntity>{

        val typeToken = object : TypeToken<List<PhotoEntity>>(){

        }.type
        return Gson().fromJson(json,typeToken)
    }
}