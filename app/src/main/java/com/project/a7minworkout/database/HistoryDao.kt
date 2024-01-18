package com.project.a7minworkout.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Insert
    suspend fun insert(historyEntity: HistoryEntity)

    @Query("SELECT * FROM `HISTORY-TABLE`")
    fun fetchAllDates(): Flow<List<HistoryEntity>>
}