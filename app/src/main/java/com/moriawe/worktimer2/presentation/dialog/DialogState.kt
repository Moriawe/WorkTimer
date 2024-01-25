package com.moriawe.worktimer2.presentation.dialog

import androidx.annotation.StringRes
import com.moriawe.worktimer2.data.entity.TimeItem
import com.moriawe.worktimer2.domain.util.TimeConstant
import java.time.LocalDateTime

data class DialogState(
    val selectedItem: TimeItem? = null,
    val startTime: String = "",
    @StringRes val startTimeError: Int? = null,
    val stopTime: String = "",
    @StringRes val stopTimeError: Int? = null,
    val description: String = ""
)