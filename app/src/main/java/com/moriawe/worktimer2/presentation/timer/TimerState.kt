package com.moriawe.worktimer2.presentation.timer

import com.moriawe.worktimer2.data.entity.TimeItem2
import java.time.Duration
import java.time.LocalDateTime

data class TimerState(
    val timeItems: List<TimeItem2> = emptyList(),
    val startTime: LocalDateTime? = null,
    val stopTime: LocalDateTime? = null,
    val description: String = "",
    val totalTime: Duration = Duration.between(startTime,stopTime),
    val isModifyingTimeCard: Boolean = false,
    //val sortType: SortType = SortType.MONTH
)
