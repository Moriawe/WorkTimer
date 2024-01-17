package com.moriawe.worktimer2.presentation.timer

import java.time.LocalDateTime

sealed interface TimerEvent {
    object SaveTimeItem: TimerEvent
    data class SetStartTime(val startTime: LocalDateTime): TimerEvent
    data class SetStopTime(val stopTime: LocalDateTime): TimerEvent
    data class SetDescription(val description: String): TimerEvent
    object StartTimer: TimerEvent
    object StopTimer: TimerEvent
    object ShowDialog: TimerEvent
    object HideDialog: TimerEvent
    //data class SortTimeCards(val sortType: SortType): TimerEvent
}

enum class SortType {
    MONTH,
    DAY,
}