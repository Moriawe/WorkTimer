package com.moriawe.worktimer2

import androidx.annotation.StringRes

enum class Screen(@StringRes val title: Int) {
    Timer(title = R.string.timer),
    TimeSheet(title = R.string.time_sheet)
}