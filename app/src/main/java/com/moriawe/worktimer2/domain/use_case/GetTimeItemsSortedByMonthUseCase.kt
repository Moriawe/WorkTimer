package com.moriawe.worktimer2.domain.use_case

import android.util.Log
import com.moriawe.worktimer2.data.TimeRepository
import com.moriawe.worktimer2.domain.model.Day
import com.moriawe.worktimer2.domain.model.Month
import com.moriawe.worktimer2.domain.util.calculateTotalTime
import com.moriawe.worktimer2.domain.util.formatDurationInHHMMToString
import com.moriawe.worktimer2.domain.util.formatDurationInHHToString
import com.moriawe.worktimer2.domain.util.mapTimeItemToTimeCardItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import java.time.Duration
import javax.inject.Inject

class GetTimeItemsSortedByMonthUseCase @Inject constructor(
    private val repo: TimeRepository
) {

    operator fun invoke(): Flow<List<Month>> {
        val TAG = "MAPPING LISTS"
        val timeItems = repo.getTimeItems()

        // -*- Groups the list by day and month -*- //
        val monthList = timeItems.map { timeList ->
            timeList
                .sortedBy { it.startTime }
                .map { time -> mapTimeItemToTimeCardItem(time) }
                .groupBy { it.month }
                .map {
                    // -*- Calculates total work time / day -*- //
                    val sortedDays = it.value
                        .groupBy { timeCardItem -> timeCardItem.date }
                        .map { (date, items) ->
                            val totalDayTime = calculateTotalTime(items)
                            Day(
                                date = date,
                                items = items,
                                totalWorkTime = formatDurationInHHMMToString(totalDayTime))
                        }
                        .sortedBy { day -> day.date }
                    // -*- Calculates total work time / month -*- //
                    val totalMonthTime = calculateTotalTime(sortedDays.flatMap { it.items })
                    Month(
                        name = it.key,
                        days = sortedDays,
                        totalWorkTimeInHours = formatDurationInHHToString(totalMonthTime))
                }
        // -*- For logging reasons only -*- //
        }.onEach { list ->
            Log.d(TAG, "list -> Months $list")
        }

        return monthList
    }
}