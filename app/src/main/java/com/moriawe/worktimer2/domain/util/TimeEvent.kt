package com.moriawe.worktimer2.domain.util

import com.moriawe.worktimer2.data.entity.TimeItem2
import java.time.LocalDateTime

sealed interface TimeEvent {
    object SaveTimeItem: TimeEvent
    data class SetStartTime(val startTime: LocalDateTime): TimeEvent
    data class SetStopTime(val stopTime: LocalDateTime): TimeEvent
    data class SetDescription(val description: String): TimeEvent
    data class DeleteTime(val timeItem2: TimeItem2): TimeEvent
    object ShowDialog: TimeEvent
    object HideDialog: TimeEvent
    //data class SortTimeCards(val sortType: SortType): TimeEvent
}

enum class SortType {
    MONTH,
    DAY,
}