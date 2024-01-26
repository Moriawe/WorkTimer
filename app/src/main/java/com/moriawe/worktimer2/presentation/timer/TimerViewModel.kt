package com.moriawe.worktimer2.presentation.timer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moriawe.worktimer2.data.entity.TimeItem
import com.moriawe.worktimer2.domain.repository.TimeRepository
import com.moriawe.worktimer2.domain.use_case.GetListOfMonthUseCase
import com.moriawe.worktimer2.domain.use_case.GetTimeItemsForSpecificDateUseCase
import com.moriawe.worktimer2.domain.use_case.ValidateStartTimeUseCase
import com.moriawe.worktimer2.domain.use_case.ValidateStopTimeUseCase
import com.moriawe.worktimer2.domain.util.TimeConstant
import com.moriawe.worktimer2.domain.util.calculateTotalTime
import com.moriawe.worktimer2.domain.util.generateAndInsertMockTimeItemsIntoDatabase
import com.moriawe.worktimer2.presentation.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class TimerViewModel  @Inject constructor(
    private val repo: TimeRepository,
    private val validateStopTimeUseCase: ValidateStopTimeUseCase,
    private val validateStartTimeUseCase: ValidateStartTimeUseCase,
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
            timeItems = timeItems,
            totalWorkTime = calculateTotalTime(timeItems)
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TimerState())


    // -*- UI EVENT FLOW -*- //
    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: TimerEvent) {
        when (event) {
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

            is TimerEvent.DeleteTimeItem -> {
                TODO()
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

    // -*- Run to get mock data to test on -*- //
    private fun generateAndInsertMockData(itemCount: Int) {
        viewModelScope.launch {
            generateAndInsertMockTimeItemsIntoDatabase(repo, itemCount)
        }
    }
}

/*

                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            message = R.string.time_format_error
                        )
                    )
 */

