package com.moriawe.worktimer2.domain.use_case

import com.moriawe.worktimer2.data.TimeRepository
import com.moriawe.worktimer2.domain.model.Month
import com.moriawe.worktimer2.domain.util.formatDurationInHHToString
import com.moriawe.worktimer2.domain.util.mapTimeItemToTimeCardItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Duration
import javax.inject.Inject

class GetTimeItemsSortedByMonthUseCase @Inject constructor(
    private val repo: TimeRepository
) {
    operator fun invoke(): Flow<List<Month>> {
        val timeItems = repo.getTimeItems()

        // -*- Maps the timeItems into TimeCardItems, checks month and then sorts them into a list of Months -*- //
        var monthList = timeItems.map { timeList ->
            timeList
                .sortedBy { it.startTime }
                .map { time -> mapTimeItemToTimeCardItem(time) }
                .groupBy { it.month }
                .map {
                    Month(it.key, it.value)
                }
        }

        // -*- Takes the list in each month and calculates the total worktime -*- //
        return monthList.map { list ->
            list.map { month ->
                val totalWorkTime = month.items.map {
                    it.totalTimeInDuration
                }.reduce(Duration::plus)
                Month(
                    name = month.name,
                    items = month.items,
                    totalWorkTimeInHours = formatDurationInHHToString(totalWorkTime)
                )
            }

        }
    }
}



