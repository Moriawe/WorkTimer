package com.moriawe.worktimer2.presentation.dialog

sealed interface DialogEvent {
    data class SetStartTime(val startTime: String): DialogEvent
    data class SetStopTime(val stopTime: String): DialogEvent
    data class SetDescription(val description: String): DialogEvent
    data class UpdateTimeItem(val onSuccess: (Boolean) -> Unit): DialogEvent
    data object HideDialog: DialogEvent
}