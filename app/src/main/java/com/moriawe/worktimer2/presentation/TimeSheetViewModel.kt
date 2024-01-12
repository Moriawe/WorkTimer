package com.moriawe.worktimer2.presentation

import androidx.lifecycle.ViewModel
import com.moriawe.worktimer2.data.generateTimeItemList
import com.moriawe.worktimer2.domain.model.TimeCardItem
import com.moriawe.worktimer2.domain.model.TimeItem
import java.time.format.DateTimeFormatter

class TimeSheetViewModel() : ViewModel() {

    val timeItemList = generateTimeItemList()

    // Takes the list of timeItems, maps them to TimeCardItems and then sorts them
    // into categories depending on the month
    val overViewList = timeItemList.map { time ->
        mapTimeItem(time)
    }.groupBy { it.month }.toSortedMap().map {
        Month(it.key, it.value)
    }

}

fun mapTimeItem(timeItem: TimeItem): TimeCardItem {

    val dateFormatter = DateTimeFormatter.ofPattern("dd MMM")
    val monthFormatter = DateTimeFormatter.ofPattern("MMM")
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    val date = timeItem.startTime.format(dateFormatter)
    val month = timeItem.startTime.format(monthFormatter)
    val startTime = timeItem.startTime.format(timeFormatter)
    val endTime = timeItem.stopTime.format(timeFormatter)
    val hours = timeItem.totalTimeInDuration.toHoursPart()
    val minutes = timeItem.totalTimeInDuration.toMinutesPart()
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

data class Month(
    val name: String,
    val items: List<TimeCardItem>
)