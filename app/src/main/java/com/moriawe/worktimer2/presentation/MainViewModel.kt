package com.moriawe.worktimer2.presentation

import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moriawe.worktimer2.R
import com.moriawe.worktimer2.domain.util.CsvExporter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val csvExporter: CsvExporter,
) : ViewModel() {

    val TAG = "MAIN VIEW MODEL"

    // -*- INTENT DATA -*- //
    private val _exportIntent = MutableLiveData<Intent?>()
    val exportIntent: LiveData<Intent?> = _exportIntent


    // -*- ON EVENT -*- //
    fun onEvent(event: MainEvent) {
        when (event) {
            MainEvent.ExportToCSV -> {
                viewModelScope.launch {
                    exportToCSV()
                }
            }
        }
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

    // -*- CSV EXPORTS -*- //
    private suspend fun exportToCSV() {
        try {
            _exportIntent.value = csvExporter.exportDataToCsv()
        }
        catch(e: ActivityNotFoundException) {
            e.printStackTrace()
            showSnackbar(R.string.network_error)
        }
    }

}