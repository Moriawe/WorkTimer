package com.moriawe.worktimer2.navigation

import androidx.annotation.DrawableRes
import com.moriawe.worktimer2.R
import com.moriawe.worktimer2.domain.util.TimeFormatters.dayFormatter
import java.time.LocalDateTime

enum class Screen(
    val route: String,
    val title: String,
    @DrawableRes val icon: Int
) {
    Timer("Timer", LocalDateTime.now().format(dayFormatter), R.drawable.timer),
    TimeSheet("TimeSheet", "Overview", R.drawable.calendar),
    Settings("Settings", "Settings", R.drawable.settings),
    Modifier("Modifier", "Modify Time Card", R.drawable.edit)
}

