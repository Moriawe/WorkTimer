package com.moriawe.worktimer2.presentation

sealed interface MainEvent {

data object ExportToCSV: MainEvent

}