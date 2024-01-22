package com.moriawe.worktimer2.domain.mapper

import com.moriawe.worktimer2.data.entity.TimeItem
import com.moriawe.worktimer2.domain.model.TimeCardItem
import com.moriawe.worktimer2.domain.util.TimeFormatters
import com.moriawe.worktimer2.domain.util.formatDurationInHHMMToString
import java.time.Duration

fun mapTimeItemToTimeCardItem(timeItem: TimeItem): TimeCardItem {

    val date = timeItem.startTime.format(TimeFormatters.dateFormatter)
    val month = timeItem.startTime.format(TimeFormatters.monthFormatter)
    val startTime = timeItem.startTime.format(TimeFormatters.timeFormatter)
    val endTime = timeItem.stopTime.format(TimeFormatters.timeFormatter)
    val duration = Duration.between(timeItem.startTime, timeItem.stopTime)
    val totalTimeString = formatDurationInHHMMToString(duration)

    return TimeCardItem(
        date = date,
        month = month,
        startTime = startTime,
        endTime = endTime,
        totalTimeInString = totalTimeString,
        totalTimeInDuration = Duration.between(timeItem.startTime, timeItem.stopTime),
        description = timeItem.description
    )
}