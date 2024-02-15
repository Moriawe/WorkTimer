package com.moriawe.worktimer2.presentation

import androidx.lifecycle.ViewModel
import com.moriawe.worktimer2.domain.util.CsvExporter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val csvExporter: CsvExporter
) : ViewModel() {

    val TAG = "MAIN VIEW MODEL"

    // -*- CSV EXPORTS -*- //
    fun exportToCSV() {
        csvExporter.exportDataToCsv()
    }


    // -*- UI EVENT FLOW -*- //
    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private suspend fun showSnackbar(message: Int) {
        _eventFlow.emit(
            UiEvent.ShowSnackbar(
                message = message
            )
        )
    }
}