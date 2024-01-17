package com.moriawe.worktimer2.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.moriawe.worktimer2.data.entity.TimeItem2
import dagger.Provides
import kotlinx.coroutines.flow.Flow

@Dao
interface TimeDao {

    @Insert
    suspend fun insertTimeItem(timeItem2: TimeItem2)

    @Update
    suspend fun updateTimeItem(timeItem2: TimeItem2)

    @Delete
    suspend fun deleteTimeItem(timeItem2: TimeItem2)

    @Query("SELECT * FROM TimeItem2")
    fun getTimeItems(): Flow<List<TimeItem2>>

}