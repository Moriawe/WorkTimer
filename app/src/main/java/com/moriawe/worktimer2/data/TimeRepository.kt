package com.moriawe.worktimer2.data

import com.moriawe.worktimer2.data.entity.TimeItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TimeRepository @Inject constructor(
    private val timeDao: TimeDao
) {

    suspend fun insertTimeItem(timeItem: TimeItem) {
        timeDao.insertTimeItem(timeItem)
    }

    suspend fun updateTimeItem(timeItem: TimeItem) {
        timeDao.updateTimeItem(timeItem)
    }

    suspend fun upsertTimeItem(timeItem: TimeItem) {
        timeDao.upsertTimeItem(timeItem)
    }

    suspend fun deleteTimeItem(timeItem: TimeItem) {
        timeDao.deleteTimeItem(timeItem)
    }

    fun getTimeItems(): Flow<List<TimeItem>> {
        return timeDao.getTimeItems()
    }
}