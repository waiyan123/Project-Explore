package com.itachi.explore.persistence.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.itachi.core.domain.PagodaVO
import com.itachi.explore.persistence.entities.AncientEntity
import com.itachi.explore.persistence.entities.PagodaEntity
import io.reactivex.Completable

@Dao
interface PagodaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPagodaList(pagodaList : List<PagodaEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPagoda(pagodaEntity : PagodaEntity)

    @Query("SELECT * FROM pagoda_table")
    suspend fun getPagodaList() : List<PagodaEntity>

    @Query("SELECT * FROM pagoda_table WHERE item_id = :itemId")
    suspend fun getPagodaById(itemId: String) : PagodaEntity

    @Query("DELETE FROM pagoda_table")
    suspend fun deletePagodaList()

    @Query("DELETE FROM pagoda_table WHERE item_id = :itemId")
    suspend fun deletePagodaById(itemId : String)

    suspend fun isPagodasExistInDb() : Boolean {
        return getPagodaList()!= null
    }
}