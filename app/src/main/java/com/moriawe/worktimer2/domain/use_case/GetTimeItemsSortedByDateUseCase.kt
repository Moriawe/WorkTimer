package com.moriawe.worktimer2.domain.use_case

import android.util.Log
import com.moriawe.worktimer2.data.TimeRepository
import com.moriawe.worktimer2.data.entity.TimeItem
import com.moriawe.worktimer2.domain.model.Month
import com.moriawe.worktimer2.domain.model.TimeCardItem
import com.moriawe.worktimer2.domain.util.TimeFormatters.dateFormatter
import com.moriawe.worktimer2.domain.util.TimeFormatters.monthFormatter
import com.moriawe.worktimer2.domain.util.TimeFormatters.timeFormatter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Duration
import java.time.format.DateTimeFormatter
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
                .map { time -> mapTimeItem(time) }
                .groupBy { it.month }
                .map { Month(it.key, it.value) }
        }
    }

    // TODO: Refactor into a mapper
    fun mapTimeItem(timeItem: TimeItem): TimeCardItem {

        Log.d("TimeSheetViewModel", "$timeItem")

        val date = timeItem.startTime.format(dateFormatter)
        val month = timeItem.startTime.format(monthFormatter)
        val startTime = timeItem.startTime.format(timeFormatter)
        val endTime = timeItem.stopTime.format(timeFormatter)
        val duration = Duration.between(timeItem.startTime, timeItem.stopTime)
        val hours = duration.toHoursPart()
        val minutes = duration.toMinutesPart()
        val totalTimeString = "${hours}h ${minutes}m"

        return TimeCardItem(
            date = date,
            month = month,
            startTime = startTime,
            stopTime = endTime,
            totalTime = totalTimeString,
            description = timeItem.description
        )
    }

}




