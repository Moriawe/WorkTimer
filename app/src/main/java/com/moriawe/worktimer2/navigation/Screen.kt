package com.moriawe.worktimer2.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.moriawe.worktimer2.R
import com.moriawe.worktimer2.domain.util.TimeFormatters.dayFormatter
import java.time.LocalDateTime

sealed class Screen(
    val route: String,
    @StringRes val title: Int,
    @DrawableRes val icon: Int
) {
    data object TimerScreen :
        Screen("Timer", R.string.daily_time_cards, R.drawable.timer)
    data object TimeSheetScreen : Screen("TimeSheet", R.string.worktime_overview, R.drawable.calendar)
    data object SettingsScreen : Screen("Settings", R.string.settings, R.drawable.settings)
    data object DialogScreen : Screen("Dialog", R.string.dialog, R.drawable.edit)

    // -*- Helper function to build a correct navigation route -*- //
    // -*- when you need to send an argument -*- //
    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }

    fun withArgs(vararg args: Int): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }

    // -*- Iterate through class -*- //
    companion object {
        fun fromRouteString(route: String?): Screen {
            return Screen::class.sealedSubclasses
                .firstOrNull { it.objectInstance?.route == route }
                ?.objectInstance
                ?: TimerScreen
        }

    }
}



// -*- Alternative to sealed class (if you don't need arguments) -*- //

enum class Screen2(
    val route: String,
    val title: String,
    @DrawableRes val icon: Int
) {
    Timer("Timer", LocalDateTime.now().format(dayFormatter), R.drawable.timer),
    TimeSheet("TimeSheet", "Overview", R.drawable.calendar),
    Settings("Settings", "Settings", R.drawable.settings),
    Dialog("Dialog", "Modify Time Card", R.drawable.edit)
}