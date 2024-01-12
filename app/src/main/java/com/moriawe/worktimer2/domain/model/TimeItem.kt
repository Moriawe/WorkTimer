package com.moriawe.worktimer2.domain

import java.time.Duration
import java.time.LocalDateTime

data class TimeItem(
    val startTime: LocalDateTime?,
    val endTime: LocalDateTime?,
    val totalTimeInDuration: Duration,
    var description: String
    )