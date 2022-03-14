package com.itachi.explore.persistence.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.itachi.core.domain.AncientVO
import com.itachi.explore.persistence.entities.AncientEntity

@Dao
interface AncientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAncientList(ancientList : List<AncientEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAncient(ancientEntity: AncientEntity)

    @Query("SELECT * FROM ancient_table")
    suspend fun getAncientsList() : List<AncientEntity>

    @Query("SELECT * FROM ancient_table WHERE item_id = :itemId")
    suspend fun getAncientById(itemId: String) : AncientEntity

    @Query("DELETE FROM ancient_table")
    suspend fun deleteAncientList()

    @Query("DELETE FROM ancient_table WHERE item_id = :itemId")
    suspend fun deleteAncientById(itemId : String)

    suspend fun isAncientsExistInDb() : Boolean {
        return getAncientsList()!= null
    }
}