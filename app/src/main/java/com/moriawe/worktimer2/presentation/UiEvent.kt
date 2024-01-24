package com.moriawe.worktimer2.presentation

import androidx.annotation.StringRes

sealed class UiEvent {
    data class ShowSnackbar(@StringRes val message: Int): UiEvent()
}