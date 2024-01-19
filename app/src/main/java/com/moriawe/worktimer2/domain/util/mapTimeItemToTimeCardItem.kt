package com.moriawe.worktimer2.domain.util

import com.moriawe.worktimer2.data.entity.TimeItem
import com.moriawe.worktimer2.domain.model.TimeCardItem
import java.time.Duration

fun mapTimeItemToTimeCardItem(timeItem: TimeItem): TimeCardItem {

    val date = timeItem.startTime.format(TimeFormatters.dateFormatter)
    val month = timeItem.startTime.format(TimeFormatters.monthFormatter)
    val startTime = timeItem.startTime.format(TimeFormatters.timeFormatter)
    val endTime = timeItem.stopTime.format(TimeFormatters.timeFormatter)
    val duration = Duration.between(timeItem.startTime, timeItem.stopTime)
    val hours = duration.toHoursPart()
    val minutes = duration.toMinutesPart()
    val totalTimeString = "${hours}h ${minutes}m"

    return TimeCardItem(
        date = date,
        month = month,
        startTime = startTime,
        endTime = endTime,
        totalTime = totalTimeString,
        description = timeItem.description
    )
}