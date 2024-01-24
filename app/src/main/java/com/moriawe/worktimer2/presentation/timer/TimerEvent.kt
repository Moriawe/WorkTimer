package com.moriawe.worktimer2.presentation.timer

import com.moriawe.worktimer2.data.entity.TimeItem
import java.time.LocalDateTime

sealed interface TimerEvent {
    data class SetStartTime(val startTime: String): TimerEvent
    data class SetStopTime(val stopTime: String): TimerEvent
    data class SetDescription(val description: String): TimerEvent
    data class DeleteTimeItem(val item: TimeItem): TimerEvent
    data object UpdateTimeItem: TimerEvent
    data object StartTimer: TimerEvent
    data object StopTimer: TimerEvent
    data class ShowDialog(val timeItem: TimeItem): TimerEvent
    data object HideDialog: TimerEvent
    data object ShowError: TimerEvent
}
