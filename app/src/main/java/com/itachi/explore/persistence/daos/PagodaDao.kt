package com.itachi.explore.persistence.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.itachi.explore.persistence.entities.PagodaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PagodaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPagodaList(pagodaList : List<PagodaEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPagoda(pagodaEntity : PagodaEntity)

    @Query("SELECT * FROM pagoda_table")
    fun getPagodaList() : Flow<List<PagodaEntity>>

    @Query("SELECT * FROM pagoda_table WHERE item_id = :itemId")
    fun getPagodaById(itemId: String) : Flow<PagodaEntity>

    @Query("DELETE FROM pagoda_table")
    suspend fun deletePagodaList()

    @Query("DELETE FROM pagoda_table WHERE item_id = :itemId")
    suspend fun deletePagodaById(itemId : String)

    suspend fun isPagodasExistInDb() : Boolean {
        return getPagodaList()!= null
    }
}