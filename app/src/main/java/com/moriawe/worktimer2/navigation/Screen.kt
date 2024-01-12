package com.moriawe.worktimer2.navigation

import androidx.annotation.StringRes
import com.moriawe.worktimer2.R

enum class Screen(@StringRes val title: Int) {
    Timer(title = R.string.timer),
    TimeSheet(title = R.string.time_sheet)
}