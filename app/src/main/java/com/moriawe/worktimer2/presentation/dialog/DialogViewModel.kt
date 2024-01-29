package com.moriawe.worktimer2.presentation.dialog

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moriawe.worktimer2.data.entity.TimeItem
import com.moriawe.worktimer2.domain.repository.TimeRepository
import com.moriawe.worktimer2.domain.use_case.UpdateTimeItemInDatabaseUseCase
import com.moriawe.worktimer2.domain.use_case.ValidateStartTimeUseCase
import com.moriawe.worktimer2.domain.use_case.ValidateStopTimeUseCase
import com.moriawe.worktimer2.domain.util.parseTimeStamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DialogViewModel  @Inject constructor(
    //private val repo: TimeRepository,
    private val updateTimeItemInDatabaseUseCase: UpdateTimeItemInDatabaseUseCase,
    private val validateStopTimeUseCase: ValidateStopTimeUseCase,
    private val validateStartTimeUseCase: ValidateStartTimeUseCase,
) : ViewModel() {

    val TAG = "DIALOG VIEW MODEL"

    // -*- DIALOG STATE -*- //
    private val _dialogState = MutableStateFlow(DialogState())
    val dialogState: StateFlow<DialogState> = _dialogState.asStateFlow()

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

    private fun updateTime(): Boolean {
        val startTimeResult = validateStartTimeUseCase.execute(
            startTime = dialogState.value.startTime
        )
        val stopTimeResult = validateStopTimeUseCase.execute(
            startTime = dialogState.value.startTime,
            stopTime = dialogState.value.stopTime
        )
        val hasError = listOf(
            startTimeResult,
            stopTimeResult
        ).any { !it.successful }

        if (hasError) {
            _dialogState.update {
                it.copy(
                    startTimeError = startTimeResult.errorMessage,
                    stopTimeError = stopTimeResult.errorMessage
                )
            }
            return false
        }

        val timeItem = TimeItem(
            id = dialogState.value.selectedItem!!.id,
            startTime = parseTimeStamp(dialogState.value.startTime),
            stopTime = parseTimeStamp(dialogState.value.stopTime),
            description = dialogState.value.description
        )
        viewModelScope.launch {
            updateTimeItemInDatabaseUseCase(timeItem)
            Log.d(TAG, "Updating timeItem $timeItem")
            //repo.updateTimeItem(timeItem = timeItem)
            _dialogState.value = DialogState()
        }
        return true

    }

}