package com.itachi.explore.persistence.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.itachi.explore.persistence.entities.AncientEntity
import com.itachi.explore.persistence.entities.ViewEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface ViewDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertViewList(viewList : List<ViewEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addView(viewEntity: ViewEntity)

    @Query("SELECT * FROM view_table")
    fun getViewsList() : Flow<List<ViewEntity>>

    @Query("SELECT * FROM view_table WHERE item_id = :itemId")
    fun getViewById(itemId: String) : Flow<ViewEntity>

    @Query("DELETE FROM view_table")
    suspend fun deleteViewList()

    @Query("DELETE FROM view_table WHERE item_id = :itemId")
    suspend fun deleteViewById(itemId : String)

    suspend fun isViewsExistInDb() : Boolean {
        return getViewsList()!= null
    }
}