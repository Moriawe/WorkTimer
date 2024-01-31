package com.moriawe.worktimer2.presentation.dialog

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moriawe.worktimer2.data.entity.TimeItem
import com.moriawe.worktimer2.domain.use_case.GetTimeItemById
import com.moriawe.worktimer2.domain.use_case.RepositoryResults
import com.moriawe.worktimer2.domain.use_case.UpdateTimeItemInDatabase
import com.moriawe.worktimer2.domain.use_case.validations.ValidateStartTimeUseCase
import com.moriawe.worktimer2.domain.use_case.validations.ValidateStopTimeUseCase
import com.moriawe.worktimer2.domain.util.TimeFormatters.timeFormatter
import com.moriawe.worktimer2.domain.util.parseTimeStamp
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime

@HiltViewModel(assistedFactory = DialogViewModel.DialogViewModelFactory::class)
class DialogViewModel  @AssistedInject constructor(
    @Assisted val id: Int?,
    private val getTimeItemById: GetTimeItemById,
    private val updateTimeItemInDatabase: UpdateTimeItemInDatabase,
    private val validateStopTimeUseCase: ValidateStopTimeUseCase,
    private val validateStartTimeUseCase: ValidateStartTimeUseCase,
) : ViewModel() {

    val TAG = "DIALOG VIEW MODEL"

    // -*- Hilt ViewModelFactory to receive the timeItem id sent from navigation -*- //
    @AssistedFactory
    interface DialogViewModelFactory {
        fun create(id: Int?): DialogViewModel
    }

    init {
        // Try to update the dialog with the correct values.
        id?.let { fetchTimeItem(it) }
    }

    // -*- DIALOG STATE -*- //
    private val _dialogState = MutableStateFlow(DialogState())
    val dialogState: StateFlow<DialogState> = _dialogState.asStateFlow()

    // -*- ON EVENT -*- //
    fun onEvent(event: DialogEvent) {
        when (event) {
            DialogEvent.HideDialog -> {
                Log.d(TAG, "Hide Dialog and reset DialogState")

                _dialogState.value = DialogState()
            }
            is DialogEvent.SetDescription -> {
                _dialogState.update {
                    it.copy(
                        description = event.description
                    )
                }
            }
            is DialogEvent.SetStartTime -> {
                _dialogState.update {
                    it.copy(
                        startTime = event.startTime
                    )
                }
            }
            is DialogEvent.SetStopTime -> {
                _dialogState.update {
                    it.copy(
                        stopTime = event.stopTime
                    )
                }
            }
            is DialogEvent.UpdateTimeItem -> {
                event.onSuccess(updateTime())
            }
        }
    }

    // -*- HELPER FUNCTIONS -*- //
    private fun fetchTimeItem(id: Int) {
        viewModelScope.launch {
            // Try to get the timeItem from the database
            val result = getTimeItemById(id)
            when (result) {
                is RepositoryResults.Success -> {
                    // If successful, update dialogstate with the correct values
                    _dialogState.update {
                        it.copy(
                            selectedItem = result.timeItem,
                            startTime = result.timeItem.startTime.format(timeFormatter),
                            stopTime = result.timeItem.stopTime.format(timeFormatter),
                            description = result.timeItem.description
                        )
                    }
                }
                is RepositoryResults.Error -> {
                    // if not successful, display the error
                    Log.e(TAG, "Error fetching time item: ${result.message}")
                }
            }
        }
    }

    private fun updateTime(): Boolean {
        // TODO: Find a way to validate start and stop-time and show correct error to user
        val startTimeResult = validateStartTimeUseCase.execute(
            startTime = dialogState.value.startTime
        )
        val stopTimeResult = validateStopTimeUseCase.execute(
            startTime = dialogState.value.startTime,
            stopTime = dialogState.value.stopTime
        )
        // Check if there was any errors, if not - it was successful!
        val hasError = listOf(
            startTimeResult,
            stopTimeResult
        ).any { !it.successful }

        // If error - display error to user
        if (hasError) {
            _dialogState.update {
                it.copy(
                    startTimeError = startTimeResult.errorMessage,
                    stopTimeError = stopTimeResult.errorMessage
                )
            }
            return false
        }

        // TODO: Should this be done somewhere else?
        // Update the timeItem from the dialogstate

        val timeItem = TimeItem(
            id = dialogState.value.selectedItem!!.id,
            startTime = turnStringIntoLocalDateTime(dialogState.value.startTime),
            stopTime = turnStringIntoLocalDateTime(dialogState.value.stopTime),
            description = dialogState.value.description
        )
        // Try to update the item in the database
        // TODO: Should check if it worked, if not, handle error
        viewModelScope.launch {
            updateTimeItemInDatabase(timeItem)
            Log.d(TAG, "Updating timeItem $timeItem")
            _dialogState.value = DialogState()
        }
        return true
    }

    private fun turnStringIntoLocalDateTime(time: String): LocalDateTime {
        return LocalDateTime.of(
            dialogState.value.selectedItem?.startTime?.toLocalDate(),
            parseTimeStamp(time)
        )
    }

}