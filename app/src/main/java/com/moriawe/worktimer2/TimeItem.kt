package com.moriawe.worktimer2

import java.time.Duration
import java.time.LocalDateTime

data class TimeItem(
    val timeStamp: LocalDateTime,
    val timeAsText: String,
    val isStartTime: Boolean
)

data class TimeItem2(
    val startTime: LocalDateTime?,
    val endTime: LocalDateTime?,
    val totalTimeInString: String,
    val totalTimeInSeconds: Long,
    val totalTimeInDuration: Duration,
    val description: String
    )