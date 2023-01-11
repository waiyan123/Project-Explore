package com.itachi.explore.persistence.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.itachi.explore.persistence.entities.AncientEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AncientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAncientList(ancientList : List<AncientEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAncient(ancientEntity: AncientEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateAncient(ancientEntity: AncientEntity)

    @Query("SELECT * FROM ancient_table")
    fun getAncientsList() : Flow<List<AncientEntity>>

    @Query("SELECT * FROM ancient_table WHERE item_id = :itemId")
    fun getAncientById(itemId: String) : Flow<AncientEntity>

    @Query("DELETE FROM ancient_table")
    suspend fun deleteAncientList()

    @Query("DELETE FROM ancient_table WHERE item_id = :itemId")
    suspend fun deleteAncientById(itemId : String)

    suspend fun isAncientsExistInDb() : Boolean {
        return getAncientsList()!= null
    }
}