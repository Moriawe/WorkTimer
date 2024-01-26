package com.moriawe.worktimer2.data

import com.moriawe.worktimer2.data.entity.CurrentStartTime
import com.moriawe.worktimer2.data.entity.TimeItem
import com.moriawe.worktimer2.domain.repository.TimeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TimeRepositoryImpl @Inject constructor(
    private val timeDao: TimeDao
): TimeRepository {

    override suspend fun insertTimeItem(timeItem: TimeItem) {
        timeDao.insertTimeItem(timeItem)
    }

    override suspend fun updateTimeItem(timeItem: TimeItem) {
        timeDao.updateTimeItem(timeItem)
    }

    override suspend fun upsertTimeItem(timeItem: TimeItem) {
        timeDao.upsertTimeItem(timeItem)
    }

    override suspend fun deleteTimeItem(timeItem: TimeItem) {
        timeDao.deleteTimeItem(timeItem)
    }

    override fun getTimeItems(): Flow<List<TimeItem>> {
        return timeDao.getTimeItems()
    }

}