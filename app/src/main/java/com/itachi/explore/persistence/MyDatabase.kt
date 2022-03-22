package com.itachi.explore.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.itachi.core.domain.PagodaVO
import com.itachi.core.domain.UserVO
import com.itachi.explore.persistence.daos.AncientDao
import com.itachi.explore.persistence.daos.PagodaDao
import com.itachi.explore.persistence.daos.UserDao
import com.itachi.explore.persistence.daos.ViewDao
import com.itachi.explore.persistence.entities.AncientEntity
import com.itachi.explore.persistence.entities.PagodaEntity
import com.itachi.explore.persistence.entities.UserEntity
import com.itachi.explore.persistence.entities.ViewEntity
import com.itachi.explore.persistence.typeconverters.CommentTypeConverter
import com.itachi.explore.persistence.typeconverters.PhotoVOListTypeConverter
import com.itachi.explore.persistence.typeconverters.PhotoVOTypeConverter
import org.koin.core.KoinComponent

@Database (entities = [UserEntity::class, PagodaEntity::class,AncientEntity::class, ViewEntity::class],
    version = 11,exportSchema = false)
@TypeConverters(CommentTypeConverter::class, PhotoVOListTypeConverter::class,PhotoVOTypeConverter::class)
abstract class MyDatabase : RoomDatabase(),KoinComponent{

    abstract fun userDao() : UserDao
    abstract fun pagodaDao() : PagodaDao
    abstract fun ancientDao() : AncientDao
    abstract fun viewDao() : ViewDao

}