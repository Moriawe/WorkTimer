package com.moriawe.worktimer2.presentation.dialog

import com.moriawe.worktimer2.data.entity.TimeItem
import com.moriawe.worktimer2.domain.util.TimeConstant
import java.time.LocalDateTime

data class DialogState(
    val selectedItem: TimeItem? = null,
    val startTime: String = "",
    val stopTime: String = "",
    val description: String = ""
)