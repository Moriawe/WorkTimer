package com.moriawe.worktimer2.presentation.timer

import java.time.LocalDateTime

sealed interface TimerEvent {
    data object SaveTimeItem: TimerEvent
    data object StartTimer: TimerEvent
    data object StopTimer: TimerEvent
    data class SetStartTime(val startTime: LocalDateTime): TimerEvent
    data class SetStopTime(val stopTime: LocalDateTime): TimerEvent
    data class SetDescription(val description: String): TimerEvent
    data object ShowDialog: TimerEvent
    data object HideDialog: TimerEvent
    //data class SortTimeCards(val sortType: SortType): TimerEvent
}

enum class SortType {
    MONTH,
    DAY,
}