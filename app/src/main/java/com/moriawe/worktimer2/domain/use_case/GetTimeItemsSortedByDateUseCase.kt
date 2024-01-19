package com.moriawe.worktimer2.domain.use_case

import com.moriawe.worktimer2.data.TimeRepository
import com.moriawe.worktimer2.domain.model.Month
import com.moriawe.worktimer2.domain.util.mapTimeItemToTimeCardItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTimeItemsSortedByDateUseCase @Inject constructor(
    private val repo: TimeRepository
) {

    // Takes the list of timeItems, maps them to TimeCardItems and then sorts them
    // into categories depending on the month
    operator fun invoke(): Flow<List<Month>> {
        val timeItems = repo.getTimeItems()

        return timeItems.map { timeList ->
            timeList
                .sortedBy { it.startTime }
                .map { time -> mapTimeItemToTimeCardItem(time) }
                .groupBy { it.month }
                .map { Month(it.key, it.value) }
        }
    }
}




