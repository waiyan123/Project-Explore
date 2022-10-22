package com.itachi.explore.persistence.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.itachi.explore.persistence.entities.UserEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow


@Dao
abstract class UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertUser(user: UserEntity)

    @Query("SELECT * FROM user_table")
    abstract fun getUser(): Flow<UserEntity>

    @Query("DELETE FROM user_table")
    abstract fun deleteUser()

    fun userInDbExist(): Boolean {
        return getUser() != null
    }
}