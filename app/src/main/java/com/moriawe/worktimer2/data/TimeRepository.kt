package com.moriawe.worktimer2.data

import com.moriawe.worktimer2.data.entity.TimeItem2
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TimeRepository @Inject constructor(
    private val timeDao: TimeDao
) {

    suspend fun insertTimeItem(timeItem2: TimeItem2) {
        timeDao.insertTimeItem(timeItem2)
    }

    suspend fun updateTimeItem(timeItem2: TimeItem2) {
        timeDao.updateTimeItem(timeItem2)
    }

    suspend fun deleteTimeItem(timeItem2: TimeItem2) {
        timeDao.deleteTimeItem(timeItem2)
    }

    fun getTimeItems(): Flow<List<TimeItem2>> {
        return timeDao.getTimeItems()
    }
}