package com.moriawe.worktimer2.domain.use_case

import android.util.Log
import com.moriawe.worktimer2.data.TimeRepository
import com.moriawe.worktimer2.domain.model.Day
import com.moriawe.worktimer2.domain.model.Month
import com.moriawe.worktimer2.domain.util.calculateTotalTime
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

        // -*- Groups by month -*- //
        val monthList = timeItems.map { timeList ->
            timeList
                .sortedBy { it.startTime }
                .map { time -> mapTimeItemToTimeCardItem(time) }
                .groupBy { it.month }
                .map {
                    val sortedDays = it.value
                        .groupBy { timeCardItem -> timeCardItem.date }
                        .map { (date, items) ->
                            Day(date = date, items = items)
                        }
                        .sortedBy { day -> day.date }

                    Month(name = it.key, days = sortedDays)
                }
        }.onEach { list ->
            Log.d(TAG, "list -> Months $list")
        }

        // -*- Takes the list in each month and calculates the total worktime -*- //
        val monthList2 = monthList.map { list ->
            list.map { month ->
                val totalWorkTime = calculateTotalTime(month.days.flatMap { it.items })
                Month(
                    name = month.name,
                    days = month.days,
                    totalWorkTimeInHours = formatDurationInHHToString(totalWorkTime)
                )
            }
        }.onEach { list ->
            Log.d(TAG, "Months -> Months with time $list")
        }

        // Return the final sorted and processed list
        return monthList2
    }
}

/*
val TAG = "MAPPING LISTS"
        val timeItems = repo.getTimeItems()

        // -*- Groups by month -*- //
        var monthList = timeItems.map { timeList ->
            timeList
                .sortedBy { it.startTime }
                .map { time -> mapTimeItemToTimeCardItem(time) }
                .groupBy { it.month }
                .map {
                    Month(name = it.key, items = it.value)
                }
        }.onEach { list ->
            Log.d(TAG, "list -> Months $list")
        }

        // -*- Takes the list in each month and calculates the total worktime -*- //
        var monthList2 = monthList.map { list ->
            list.map { month ->
                val totalWorkTime = calculateTotalTime(month.items)
//                val totalWorkTime = month.items.map {
//                    it.totalTimeInDuration
//                }.reduce(Duration::plus)
                Month(
                    name = month.name,
                    items = month.items,
                    totalWorkTimeInHours = formatDurationInHHToString(totalWorkTime)
                )
            }
        }.onEach { list ->
            Log.d(TAG, "Months -> Months with time $list")
        }

        // -*- Takes the list in each month and sorts it into days -*- //
        var dayList =  monthList.map { list ->
            list.map { month ->
                val sortedDays = month.items
                    .groupBy { it.date }
                    .map {
                        Day(date = it.key, items = it.value)
                    }
                    .sortedBy { it.date }
                Month(
                    name = month.name,
                    days = sortedDays,
                    items =
                )
            }
        }.onEach { list ->
            Log.d(TAG, "Months -> days $list")
        }

     return monthList2
 */

