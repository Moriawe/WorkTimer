package com.moriawe.worktimer2.presentation.timer

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moriawe.worktimer2.R
import com.moriawe.worktimer2.data.entity.TimeItem
import com.moriawe.worktimer2.domain.repository.TimeRepository
import com.moriawe.worktimer2.domain.use_case.GetTimeItemsForSpecificDateUseCase
import com.moriawe.worktimer2.domain.use_case.RepositoryResults
import com.moriawe.worktimer2.domain.use_case.SaveTimeItemToDatabaseUseCase
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
    private val saveTimeItemToDatabaseUseCase: SaveTimeItemToDatabaseUseCase,
    getTimeItemsForSpecificDateUseCase: GetTimeItemsForSpecificDateUseCase
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
        // Check if there is an actual change in time
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
            when (saveTimeItemToDatabaseUseCase(timeItem)) {
                // When successful display log message
                is RepositoryResults.Success -> {
                    Log.d(TAG, "Time was saved")
                }
                // When unsuccessful, display error message to user
                is RepositoryResults.Error -> {
                    showSnackbar(R.string.error_add)
                    Log.e(TAG, "ERROR - Time wasn't saved")
                }
            }
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

    private suspend fun showSnackbar(message: Int) {
        _eventFlow.emit(
            UiEvent.ShowSnackbar(
                message = message
            )
        )
    }

    // -*- Run to get mock data to test on -*- //
    // Need to import repository in ViewModel to work
//    private fun generateAndInsertMockData(itemCount: Int) {
//        viewModelScope.launch {
//            generateAndInsertMockTimeItemsIntoDatabase(repo, itemCount)
//        }
//    }
}

