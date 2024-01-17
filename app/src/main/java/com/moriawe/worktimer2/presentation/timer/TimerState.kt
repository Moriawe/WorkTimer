package com.moriawe.worktimer2.presentation.timer

import com.moriawe.worktimer2.data.entity.TimeItem2
import com.moriawe.worktimer2.domain.util.TimeConstant
import java.time.Duration
import java.time.LocalDateTime

data class TimerState(
    val timeItems: List<TimeItem2> = emptyList(),
    val startTime: LocalDateTime = LocalDateTime.parse(TimeConstant.TIME_DEFAULT_STRING),
    val stopTime: LocalDateTime = LocalDateTime.parse(TimeConstant.TIME_DEFAULT_STRING),
    val isTimerStarted: Boolean = false,
    val description: String = "",
    val totalTimeInDuration: Duration = Duration.between(startTime,stopTime),
    val isModifyingTimeCard: Boolean = false,
    //val sortType: SortType = SortType.DAY
)
