package com.moriawe.worktimer2.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.moriawe.worktimer2.data.entity.CurrentStartTime
import com.moriawe.worktimer2.data.entity.TimeItem
import kotlinx.coroutines.flow.Flow

@Dao
interface TimeDao {

    // TODO: Flow or LiveData?
    @Query("SELECT * FROM CurrentStartTime LIMIT 1")
    fun getCurrentStartTime(): Flow<CurrentStartTime?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentStartTime(startTime: CurrentStartTime)

    @Insert
    suspend fun insertTimeItem(timeItem: TimeItem)

    @Update
    suspend fun updateTimeItem(timeItem: TimeItem)

    @Upsert
    suspend fun upsertTimeItem(timeItem: TimeItem)

    @Delete
    suspend fun deleteTimeItem(timeItem: TimeItem)

    @Query("DELETE FROM TimeItem WHERE id=:id")
    suspend fun deleteTimeItemWithId(id: Int)

    @Query("SELECT * FROM TimeItem")
    fun getTimeItems(): Flow<List<TimeItem>>

    @Query("SELECT * FROM TimeItem WHERE id=:id")
    suspend fun getItemById(id: Int): TimeItem



}