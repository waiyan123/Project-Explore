package com.itachi.explore.persistence.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.itachi.explore.persistence.entities.PhotoEntity

class PhotoVOTypeConverter {
    @TypeConverter
    fun toString(vo : PhotoEntity) :String {
        return Gson().toJson(vo)
    }

    @TypeConverter
    fun toVO(json:String) : PhotoEntity{

        val typeToken = object : TypeToken<PhotoEntity>(){

        }.type
        return Gson().fromJson(json,typeToken)
    }
}