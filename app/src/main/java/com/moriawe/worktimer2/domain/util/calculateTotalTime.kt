package com.moriawe.worktimer2.domain.util

import com.moriawe.worktimer2.data.entity.TimeItem
import java.time.Duration

fun calculateTotalTime(timeItems: List<TimeItem>): Duration {

    var totalTime: Duration = Duration.ZERO

    timeItems.forEach { item ->
        totalTime += Duration.between(item.startTime, item.stopTime)
    }
    return totalTime

}