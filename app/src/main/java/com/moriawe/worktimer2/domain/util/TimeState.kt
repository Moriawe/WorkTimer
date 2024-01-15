package com.moriawe.worktimer2.domain.util

import com.moriawe.worktimer2.data.entity.TimeItem2
import java.time.Duration
import java.time.LocalDateTime

data class TimeState(
    val timeItems: List<TimeItem2> = emptyList(),
    val startTime: LocalDateTime,
    val stopTime: LocalDateTime,
    val description: String,
    val totalTime: Duration = Duration.between(startTime,stopTime),
    val isModifyingTimeCard: Boolean = false,
    //val sortType: SortType = SortType.MONTH
)
