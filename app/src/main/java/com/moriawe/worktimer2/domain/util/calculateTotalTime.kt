package com.moriawe.worktimer2.domain.util

import com.moriawe.worktimer2.domain.model.TimeCardItem
import java.time.Duration

fun calculateTotalTime(timeItems: List<TimeCardItem>): Duration {

    var totalTime: Duration = Duration.ZERO

    timeItems.forEach { item ->
        totalTime += item.totalTimeInDuration
    }
    return totalTime

}