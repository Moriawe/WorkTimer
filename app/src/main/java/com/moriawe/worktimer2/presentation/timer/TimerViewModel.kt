package com.moriawe.worktimer2.presentation.timer

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moriawe.worktimer2.data.TimeRepository
import com.moriawe.worktimer2.data.entity.TimeItem
import com.moriawe.worktimer2.domain.use_case.GetTimeItemsByTodayUseCase
import com.moriawe.worktimer2.domain.util.TimeConstant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor(
    private val repo: TimeRepository,
    private val getTimeItemsByTodayUseCase: GetTimeItemsByTodayUseCase
) : ViewModel() {

    val TAG = "TIMER VIEW MODEL"

    private val _timeItems = getTimeItemsByTodayUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    private val _state = MutableStateFlow(TimerState())

    // This updates the state whenever there is a change in either _state or _timeItems
    val state = combine(_state, _timeItems) { state, timeItems ->
        state.copy(
            timeItems = timeItems
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TimerState())

    private val _dialogState = MutableStateFlow(DialogState())
    val dialogState: StateFlow<DialogState> = _dialogState.asStateFlow()


    fun onEvent(event: TimerEvent) {
        when (event) {
            is TimerEvent.SetDescription -> {
                _dialogState.update {
                    it.copy(
                        description = event.description
                    )
                }
            }

            is TimerEvent.SetStartTime -> {
                _dialogState.update {
                    it.copy(
                        startTime = event.startTime
                    )
                }
            }

            is TimerEvent.SetStopTime -> {
                _dialogState.update {
                    it.copy(
                        stopTime = event.stopTime
                    )
                }
            }

            is TimerEvent.UpdateTimeItem -> {
                updateTimeItem()
            }

            TimerEvent.StartTimer -> {
                _state.update {
                    it.copy(
                        startTime = LocalDateTime.now(),
                        isTimerStarted = true
                    )
                }
                Log.d(TAG, "Starttime is updated to: ${state.value.startTime} and " +
                        "the timer is started?: ${state.value.isTimerStarted}")
            }

            TimerEvent.StopTimer -> {
                _state.update {
                    it.copy(
                        stopTime = LocalDateTime.now(),
                        isTimerStarted = false,
                    )
                }
                Log.d(TAG, "StopTime is updated to: ${state.value.stopTime} and " +
                        "the timer is started?: ${state.value.isTimerStarted}")
                addNewTimeItem()
            }

            is TimerEvent.ShowDialog -> {
                _dialogState.update {
                    it.copy(
                        selectedItem = event.timeItem,
                        startTime = event.timeItem.startTime,
                        stopTime = event.timeItem.stopTime,
                        description = event.timeItem.description,
                    )
                }
                _state.update {
                    it.copy(
                        isModifyingTimeCard = true
                    )
                }
            }

            TimerEvent.HideDialog -> {
                _state.update {
                    it.copy(
                        isModifyingTimeCard = false
                    )
                }
                _dialogState.value = DialogState()
            }
        }

    }

    private fun addNewTimeItem() {
        // Null Check
        if (
            state.value.startTime == LocalDateTime.parse(
                TimeConstant.TIME_DEFAULT_STRING
            )
            || state.value.stopTime == LocalDateTime.parse(
                TimeConstant.TIME_DEFAULT_STRING
            )
        ) {
            return
        }

        val timeItem = TimeItem(
            startTime = state.value.startTime,
            stopTime = state.value.stopTime
        )

        viewModelScope.launch {
            Log.d(TAG, "Sent to Repo $timeItem")
            repo.insertTimeItem(timeItem = timeItem)
        }
        resetState()
    }

    private fun updateTimeItem() {
        if (dialogState.value.selectedItem != null) {
            val timeItem = TimeItem(
                id = dialogState.value.selectedItem!!.id,
                startTime = dialogState.value.startTime,
                stopTime = dialogState.value.stopTime,
                description = dialogState.value.description
            )
            viewModelScope.launch {
                Log.d(TAG, "Sent to Repo $timeItem")
                repo.updateTimeItem(timeItem = timeItem)
            }
        }
        _dialogState.value = DialogState()
    }


    private fun resetState() {
        _state.update {
            it.copy(
                isModifyingTimeCard = false,
                selectedItem = null,
                startTime = LocalDateTime.parse(TimeConstant.TIME_DEFAULT_STRING),
                stopTime = LocalDateTime.parse(TimeConstant.TIME_DEFAULT_STRING),
                description = ""
            )
        }
    }
}