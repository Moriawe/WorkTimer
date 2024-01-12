package com.moriawe.worktimer2.domain.model

import java.time.Duration
import java.time.LocalDateTime

data class TimeItem(
    val startTime: LocalDateTime,
    val stopTime: LocalDateTime,
    val totalTimeInDuration: Duration,
    var description: String
    )