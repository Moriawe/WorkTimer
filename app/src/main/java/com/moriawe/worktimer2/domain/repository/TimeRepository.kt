package com.moriawe.worktimer2.domain.repository

import com.moriawe.worktimer2.data.entity.CurrentStartTime
import com.moriawe.worktimer2.data.entity.TimeItem
import kotlinx.coroutines.flow.Flow

interface TimeRepository {

    suspend fun insertTimeItem(timeItem: TimeItem)

    suspend fun updateTimeItem(timeItem: TimeItem)

    suspend fun upsertTimeItem(timeItem: TimeItem)

    suspend fun deleteTimeItem(timeItem: TimeItem)

    fun getTimeItems(): Flow<List<TimeItem>>

}