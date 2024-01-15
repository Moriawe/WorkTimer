package com.moriawe.worktimer2.presentation.timer

import com.moriawe.worktimer2.data.entity.TimeItem2
import java.time.LocalDateTime

sealed interface TimerEvent {
    object SaveTimeItem: TimerEvent
    data class SetStartTime(val startTime: LocalDateTime): TimerEvent
    data class SetStopTime(val stopTime: LocalDateTime): TimerEvent
    data class SetDescription(val description: String): TimerEvent
    data class DeleteTime(val timeItem2: TimeItem2): TimerEvent
    object ShowDialog: TimerEvent
    object HideDialog: TimerEvent
    //data class SortTimeCards(val sortType: SortType): TimerEvent
}

enum class SortType {
    MONTH,
    DAY,
}