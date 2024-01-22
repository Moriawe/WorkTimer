package com.moriawe.worktimer2.presentation.timer

import com.moriawe.worktimer2.data.entity.TimeItem
import com.moriawe.worktimer2.domain.util.TimeConstant
import java.time.LocalDateTime

data class TimerState(
    val timeItems: List<TimeItem> = emptyList(),
    val selectedItem: TimeItem? = null,
    val startTime: LocalDateTime = LocalDateTime.parse(TimeConstant.TIME_DEFAULT_STRING),
    val stopTime: LocalDateTime = LocalDateTime.parse(TimeConstant.TIME_DEFAULT_STRING),
    val isTimerStarted: Boolean = false,
    val isModifyingTimeCard: Boolean = false,
)
