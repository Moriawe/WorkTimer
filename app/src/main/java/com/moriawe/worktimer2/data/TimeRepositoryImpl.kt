package com.moriawe.worktimer2.data

import com.moriawe.worktimer2.data.entity.CurrentStartTime
import com.moriawe.worktimer2.data.entity.TimeItem
import com.moriawe.worktimer2.domain.repository.TimeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TimeRepositoryImpl @Inject constructor(
    private val timeDao: TimeDao
): TimeRepository {

    override fun getCurrentStartTime(): Flow<CurrentStartTime?> {
        return timeDao.getCurrentStartTime()
    }

    override suspend fun insertCurrentStartTime(startTime: CurrentStartTime) {
        timeDao.insertCurrentStartTime(startTime)
    }

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

    override suspend fun deleteTimeItemWithId(id: Int) {
        timeDao.deleteTimeItemWithId(id)
    }

    override fun getTimeItems(): Flow<List<TimeItem>> {
        return timeDao.getTimeItems()
    }

    override suspend fun getItemById(id: Int): TimeItem {
        return timeDao.getItemById(id)
    }

}