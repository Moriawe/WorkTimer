package com.moriawe.worktimer2.presentation.time_sheet

import com.moriawe.worktimer2.domain.model.Month

data class TimeSheetState(
    val months: List<Month> = emptyList(),
)

