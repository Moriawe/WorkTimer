package com.moriawe.worktimer2.domain.model

import java.time.Duration

data class TimeCardItem(
    val date: String,
    val month: String,
    val startTime: String,
    val endTime: String,
    val totalTimeInString: String,
    val totalTimeInDuration: Duration,
    val description: String
)