package com.moriawe.worktimer2.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moriawe.worktimer2.R
import com.moriawe.worktimer2.data.TimeRepository
import com.moriawe.worktimer2.data.entity.TimeItem
import com.moriawe.worktimer2.domain.use_case.GetTimeItemsForSpecificDateUseCase
import com.moriawe.worktimer2.domain.use_case.GetListOfMonthUseCase
import com.moriawe.worktimer2.domain.util.TimeConstant
import com.moriawe.worktimer2.domain.util.TimeFormatters.timeFormatter
import com.moriawe.worktimer2.domain.util.generateAndInsertMockTimeItemsIntoDatabase
import com.moriawe.worktimer2.presentation.time_sheet.TimeSheetState
import com.moriawe.worktimer2.presentation.dialog.DialogState
import com.moriawe.worktimer2.presentation.timer.TimerEvent
import com.moriawe.worktimer2.presentation.timer.TimerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: TimeRepository,
    private val getListOfMonthUseCase: GetListOfMonthUseCase,
    private val getTimeItemsForSpecificDateUseCase: GetTimeItemsForSpecificDateUseCase
) : ViewModel() {

    val TAG = "TIMER VIEW MODEL"

    // -*- TIMER STATES -*- //
    private val _timeItems = getTimeItemsForSpecificDateUseCase(LocalDateTime.now())
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    private val _timerState = MutableStateFlow(TimerState())

    // -*- This updates the state whenever there is a change in either _state or _timeItems -*- //
    val timerState = combine(_timerState, _timeItems) { state, timeItems ->
        state.copy(
            timeItems = timeItems
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TimerState())

    // -*- DIALOG STATE -*- //
    // -*- Separate dialog state so user can update an item while still recording time -*- //
    private val _dialogState = MutableStateFlow(DialogState())
    val dialogState: StateFlow<DialogState> = _dialogState.asStateFlow()

    // -*- TIME SHEET STATES -*- //
    private val _overViewList = getListOfMonthUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _timeSheetState = MutableStateFlow(TimeSheetState())

    val timeSheetState = combine(_timeSheetState, _overViewList) { state, months ->
        state.copy(
            months = months
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TimeSheetState())

    // -*- UI EVENT FLOW -*- //
    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

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
                event.onSuccess(updateTimeItem())
            }

            is TimerEvent.DeleteTimeItem -> {

            }

            TimerEvent.StartTimer -> {
                _timerState.update {
                    it.copy(
                        startTime = LocalDateTime.now(),
                        isTimerStarted = true
                    )
                }
//                Log.d(TAG, "Start time is updated to: ${state.value.startTime} and " +
//                        "the timer is started?: ${state.value.isTimerStarted}")
            }

            TimerEvent.StopTimer -> {
                _timerState.update {
                    it.copy(
                        stopTime = LocalDateTime.now(),
                        isTimerStarted = false,
                    )
                }
//                Log.d(TAG, "StopTime is updated to: ${state.value.stopTime} and " +
//                        "the timer is started?: ${state.value.isTimerStarted}")
                addNewTimeItem()
            }

            is TimerEvent.ShowDialog -> {
                _dialogState.update {
                    it.copy(
                        selectedItem = event.timeItem,
                        startTime = event.timeItem.startTime.format(timeFormatter),
                        stopTime = event.timeItem.stopTime.format(timeFormatter),
                        description = event.timeItem.description,
                    )
                }
                _timerState.update {
                    it.copy(
                        isModifyingTimeCard = true
                    )
                }
            }

            TimerEvent.HideDialog -> {
                _timerState.update {
                    it.copy(
                        isModifyingTimeCard = false
                    )
                }
                _dialogState.value = DialogState()
            }
        }

    }

    // -*- Adds a new item to the database when time is stopped -*- //
    private fun addNewTimeItem() {
        // Check if there is an actualy change in time
        if (
            timerState.value.startTime == LocalDateTime.parse(
                TimeConstant.TIME_DEFAULT_STRING
            )
            || timerState.value.stopTime == LocalDateTime.parse(
                TimeConstant.TIME_DEFAULT_STRING
            )
        ) {
            return
        }

        val timeItem = TimeItem(
            startTime = timerState.value.startTime,
            stopTime = timerState.value.stopTime
        )

        viewModelScope.launch {
//            Log.d(TAG, "Insert item: $timeItem")
            repo.insertTimeItem(timeItem = timeItem)
        }
        resetState()
    }

    // -*- Resets the state for starting/stopping time -*- //
    private fun resetState() {
        _timerState.update {
            it.copy(
                isModifyingTimeCard = false,
                selectedItem = null,
                startTime = LocalDateTime.parse(TimeConstant.TIME_DEFAULT_STRING),
                stopTime = LocalDateTime.parse(TimeConstant.TIME_DEFAULT_STRING),
            )
        }
    }

    // -*- Updates the time or description for an item and resets the dialog state -*- //
    private fun updateTimeItem(): Boolean {
        var isSuccess = false
        // Check that we have a proper TimeItem to update
        if (dialogState.value.selectedItem != null) {

            // Check that it is the proper format to parse it
            viewModelScope.launch {
                if (isValidTimeStampFormat(dialogState.value.startTime)
                    && isValidTimeStampFormat(dialogState.value.stopTime)) {
                    Log.d(TAG, "Both start and Stop are valid timestamps")

                    val startTime = parseTimeStamp(dialogState.value.startTime)
                    val stopTime = parseTimeStamp(dialogState.value.stopTime)

                    // Check if start time is before stop time
                    if (isStopTimeAfterStartTime(startTime, stopTime)) {
                        val timeItem = TimeItem(
                            id = dialogState.value.selectedItem!!.id,
                            startTime = startTime,
                            stopTime = stopTime,
                            description = dialogState.value.description
                        )
                        // Update database
                        // TODO: Refactor to UseCase
                        isSuccess = true
                        Log.d(TAG, "Updating timeItem $timeItem")
                        repo.updateTimeItem(timeItem = timeItem)
                        _dialogState.value = DialogState()
                    } else {
                        Log.d(TAG, "ERROR start-end time incorrect order")
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = R.string.start_end_time_incorrect
                            )
                        )
                    }

                } else {
                    Log.d(TAG, "ERROR the time format is wrong")
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            message = R.string.time_format_error
                        )
                    )
                }
            }
        }
        return isSuccess
    }

    // -*- Checks if time is in the right format -*- //
    private fun isValidTimeStampFormat(timeStamp: String): Boolean {
        return try {
            Log.d(TAG, "Try $timeStamp")
            LocalTime.parse(timeStamp, timeFormatter)
            true
        } catch (e: Exception) {
            Log.d(TAG, "ERROR Not a valid timestamp")
            false
        }
    }

    private fun isStopTimeAfterStartTime(startTime: LocalDateTime, stopTime: LocalDateTime): Boolean {
        return startTime.isBefore(stopTime)
    }

    private fun parseTimeStamp(timeStamp: String): LocalDateTime {
        val time = LocalTime.parse(timeStamp, timeFormatter)
        return LocalDateTime.of(LocalDate.now(), time)
    }

    // -*- Run to get mock data to test on -*- //
    private fun generateAndInsertMockData(itemCount: Int) {
        viewModelScope.launch {
            generateAndInsertMockTimeItemsIntoDatabase(repo, itemCount)
        }
    }
}