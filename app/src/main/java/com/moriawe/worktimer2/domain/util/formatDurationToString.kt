package com.moriawe.worktimer2.domain.util

import java.time.Duration

fun formatDurationInHHToString(duration: Duration): String {
    val hours = duration.toHoursPart()
    val totalTimeString = "${hours}h"
    return totalTimeString
}

fun formatDurationInHHMMToString(duration: Duration): String {
    val hours = duration.toHoursPart()
    val minutes = duration.toMinutesPart()
    val totalTimeString = "${hours}h ${minutes}m"
    return totalTimeString
}