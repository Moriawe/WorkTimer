package com.moriawe.worktimer2.domain.util

import com.moriawe.worktimer2.data.entity.TimeItem
import com.moriawe.worktimer2.domain.model.TimeCardItem
import java.time.Duration
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object TimeFormatters {
    val defaultFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val yearMonthDayFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val dateFormatter = DateTimeFormatter.ofPattern("dd MMM")
    val monthFormatter = DateTimeFormatter.ofPattern("MMMM")
    val dayFormatter = DateTimeFormatter.ofPattern("EEEE dd MMM")
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    val dialogFormatter = DateTimeFormatter.ofPattern("HH.mm")
}

object TimeConstant {
    const val TIME_DEFAULT_STRING = "2001-01-01T01:01:01"
}

fun formatDurationInHHToString(duration: Duration): String {
    val hours = duration.toHours()
    return "${hours}h"
}

fun formatDurationInHHMMToString(duration: Duration): String {
    val totalTimeInMinutes = duration.toMinutes()
    val hours = totalTimeInMinutes / 60
    val minutes = totalTimeInMinutes % 60
    return "${hours}h ${minutes}m"
}


// TODO: Error handling if parse not possible! where?
fun parseTimeStamp(timeStamp: String): LocalTime {
    return LocalTime.parse(timeStamp, TimeFormatters.timeFormatter)
}

fun parseDialogTimeStamp(timeStamp: String): LocalTime {
    return LocalTime.parse(timeStamp, TimeFormatters.dialogFormatter)
}


fun calculateTotalTime(timeItems: List<TimeCardItem>): Duration {

    var totalTime: Duration = Duration.ZERO

    timeItems.forEach { item ->
        totalTime += item.totalTimeInDuration
    }
    return totalTime

}

fun calculateTotalTime(timeItems: List<TimeItem>): String {

    var totalTime: Duration = Duration.ZERO

    timeItems.forEach { item ->
        totalTime += Duration.between(item.startTime, item.stopTime)
    }

    return formatDurationInHHMMToString(totalTime)

}