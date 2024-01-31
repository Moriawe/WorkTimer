package com.moriawe.worktimer2.presentation.timer

sealed interface TimerEvent {
    data class DeleteTimeItem(val itemId: Int): TimerEvent
    data object StartTimer: TimerEvent
    data object StopTimer: TimerEvent
    //data class ShowDialog(val timeItem: TimeItem): TimerEvent

}
