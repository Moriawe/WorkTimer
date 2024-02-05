package com.moriawe.worktimer2.domain.util

import com.moriawe.worktimer2.data.entity.TimeItem
import com.moriawe.worktimer2.domain.model.Day
import com.moriawe.worktimer2.domain.model.TimeCardItem
import java.time.Duration

// TODO: Can this be a use case instead?
//  And is there a way to make it accept either or argument?
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