package com.moriawe.worktimer2.domain.repository

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.moriawe.worktimer2.data.entity.CurrentStartTime
import com.moriawe.worktimer2.data.entity.TimeItem
import kotlinx.coroutines.flow.Flow

interface TimeRepository {

    fun getCurrentStartTime(): Flow<CurrentStartTime?>

    suspend fun insertCurrentStartTime(startTime: CurrentStartTime)

    suspend fun insertTimeItem(timeItem: TimeItem)

    suspend fun updateTimeItem(timeItem: TimeItem)

    suspend fun upsertTimeItem(timeItem: TimeItem)

    suspend fun deleteTimeItem(timeItem: TimeItem)

    suspend fun deleteTimeItemWithId(id: Int)

    fun getTimeItems(): Flow<List<TimeItem>>

    suspend fun getItemById(id: Int): TimeItem

}