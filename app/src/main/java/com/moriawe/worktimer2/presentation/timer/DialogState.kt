package com.moriawe.worktimer2.presentation.timer

import com.moriawe.worktimer2.data.entity.TimeItem
import com.moriawe.worktimer2.domain.util.TimeConstant
import java.time.LocalDateTime

data class DialogState(
    val selectedItem: TimeItem? = null,
    val startTime: LocalDateTime = LocalDateTime.parse(TimeConstant.TIME_DEFAULT_STRING),
    val stopTime: LocalDateTime = LocalDateTime.parse(TimeConstant.TIME_DEFAULT_STRING),
    val description: String = "",
)