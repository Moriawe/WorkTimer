package com.moriawe.worktimer2.domain.util

import com.moriawe.worktimer2.domain.model.TimeItem
import java.time.Duration
import java.time.LocalDateTime
import kotlin.random.Random

fun generateTimeItemsList(numberOfItems: Int): List<TimeItem> {
    val currentDateTime = LocalDateTime.now()

    val timeItemsList = mutableListOf<TimeItem>()

    repeat(numberOfItems) {
        val randomDate = LocalDateTime.of(
            currentDateTime.year,
            Random.nextInt(1, 13),
            Random.nextInt(1, 29),
            Random.nextInt(0, 24),
            Random.nextInt(0, 60),
            Random.nextInt(0, 60)
        )

        val startTime = randomDate
        val endTime = startTime.plusHours(Random.nextLong(1, 9))
        val totalTimeInDuration = Duration.between(startTime, endTime)
        val description = "" // Empty string for description

        timeItemsList.add(
            TimeItem(
                startTime = startTime,
                stopTime = endTime,
                totalTimeInDuration = totalTimeInDuration,
                description = description
            )
        )
    }

    return timeItemsList
}