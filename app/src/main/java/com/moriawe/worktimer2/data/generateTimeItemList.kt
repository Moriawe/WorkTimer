package com.moriawe.worktimer2.data

import com.moriawe.worktimer2.domain.model.TimeItem
import java.time.Duration
import java.time.LocalDateTime

fun generateTimeItemList(): List<TimeItem> {
    val currentDateTime = LocalDateTime.now()

    // Set the startTime to the beginning of the current month
    val startTime = currentDateTime.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0)

    // Set the endOfWeek to the end of the last month
    val endTime = currentDateTime.minusMonths(1).withDayOfMonth(currentDateTime.month.length(currentDateTime.toLocalDate().isLeapYear())).withHour(23).withMinute(59).withSecond(59).withNano(999999999)

    val timeItemList = mutableListOf<TimeItem>()

    var currentTime = startTime

    while (currentTime.isBefore(endTime)) {
        val stopTime = currentTime.plusHours((1..8).random().toLong())
        val totalTimeInDuration = Duration.between(currentTime, stopTime)
        val description = "Activity ${timeItemList.size + 1}"

        timeItemList.add(
            TimeItem(
                startTime = currentTime,
                stopTime = stopTime,
                totalTimeInDuration = totalTimeInDuration,
                description = description
            )
        )

        currentTime = stopTime.plusMinutes((15..120).random().toLong())
    }

    return timeItemList
}
