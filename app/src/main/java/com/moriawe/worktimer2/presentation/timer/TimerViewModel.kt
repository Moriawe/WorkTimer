package com.moriawe.worktimer2.presentation.timer

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moriawe.worktimer2.R
import com.moriawe.worktimer2.data.entity.CurrentStartTime
import com.moriawe.worktimer2.data.entity.TimeItem
import com.moriawe.worktimer2.domain.use_case.DeleteTimeItemFromDatabase
import com.moriawe.worktimer2.domain.use_case.GetCurrentStartTime
import com.moriawe.worktimer2.domain.use_case.GetTimeItemsForSpecificDate
import com.moriawe.worktimer2.domain.use_case.RepositoryResults
import com.moriawe.worktimer2.domain.use_case.SaveCurrentStartTime
import com.moriawe.worktimer2.domain.use_case.SaveTimeItemToDatabase
import com.moriawe.worktimer2.domain.util.TimeConstant
import com.moriawe.worktimer2.domain.util.calculateTotalTime
import com.moriawe.worktimer2.domain.util.generateMockList
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

// TODO: Why are some variables and some not?

@HiltViewModel
class TimerViewModel  @Inject constructor(
    private val deleteTimeItemFromDatabase: DeleteTimeItemFromDatabase,
    private val saveTimeItemToDatabase: SaveTimeItemToDatabase,
    private val saveCurrentStartTime: SaveCurrentStartTime,
    getCurrentStartTime: GetCurrentStartTime,
    getTimeItemsForSpecificDate: GetTimeItemsForSpecificDate,
    private val generateMockList: generateMockList
) : ViewModel() {

    val TAG = "TIMER VIEW MODEL"

    // -*- TIMER STATES -*- //
    // Keeps track of if the timer is started even if app is terminated
    private val _currentStartTime = getCurrentStartTime()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    // fetches the time for today from database
    private val _timeItems = getTimeItemsForSpecificDate(LocalDateTime.now())
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    // state for this screen
    private val _timerState = MutableStateFlow(TimerState())

    // -*- This updates the state whenever there is a change in either _state or _timeItems -*- //
    val timerState = combine(_timerState, _timeItems, _currentStartTime) { state, timeItems, startTime ->
        state.copy(
            timeItems = timeItems,
            startTime = startTime?.currentStartTime ?: LocalDateTime.parse(TimeConstant.TIME_DEFAULT_STRING),
            isTimerStarted = startTime?.isTimerStarted ?: false,
            totalWorkTime = calculateTotalTime(timeItems),
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TimerState())


    // -*- UI EVENT FLOW -*- //
    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    // -*- ON EVENT -*- //
    fun onEvent(event: TimerEvent) {
        when (event) {
            TimerEvent.StartTimer -> {
                startTimer()
               Log.d(TAG, "Start time is updated to: ${timerState.value.startTime} and " +
                        "the timer is started?: ${timerState.value.isTimerStarted}")
            }

            TimerEvent.StopTimer -> {
                _timerState.update {
                    it.copy(
                        stopTime = LocalDateTime.now(),
                    )
                }
                Log.d(TAG, "StopTime is updated to: ${timerState.value.stopTime} and " +
                        "the timer is started?: ${timerState.value.isTimerStarted}")
                addNewTimeItem()
            }

            is TimerEvent.DeleteTimeItem -> {
                deleteTimeItem(event.itemId)
                Log.d(TAG, "Delete item!")
            }
        }

    }

    // -*- HELPER FUNCTIONS -*- //

    private fun startTimer() {
        viewModelScope.launch {
            // Uncomment to generate 50 objects to the database
            // generateMockList(50)
            saveCurrentStartTime(
                CurrentStartTime(
                    currentStartTime = LocalDateTime.now(),
                    isTimerStarted = true
                )
            )
        }
    }

    private fun stopTimer() {
        viewModelScope.launch {
            saveCurrentStartTime(
                CurrentStartTime(
                    currentStartTime = null,
                    isTimerStarted = false
                )
            )
        }
    }

    // -*- Adds a new item to the database when time is stopped -*- //
    private fun addNewTimeItem() {
        // Check if there is an actual change in time
        // TODO: Use null instead of a default time?
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

        // TODO: Should this be done somewhere else?
        val timeItem = TimeItem(
            startTime = timerState.value.startTime,
            stopTime = timerState.value.stopTime
        )

        viewModelScope.launch {
            when (saveTimeItemToDatabase(timeItem)) {
                // When successful display log message
                is RepositoryResults.Success -> {
                    Log.d(TAG, "SUCCESS - Time was saved")
                    stopTimer()
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

    // -*- Delete item from database and error handling -*- //
    private fun deleteTimeItem(id: Int) {
        viewModelScope.launch {
            when (deleteTimeItemFromDatabase(id)) {
                // When successful display log message
                is RepositoryResults.Success -> {
                    Log.d(TAG, "SUCCESS - Item was removed")
                }
                // When unsuccessful, display error message to user
                is RepositoryResults.Error -> {
                    showSnackbar(R.string.error_delete)
                    Log.e(TAG, "ERROR - Time wasn't deleted")
                }
            }
        }
    }

    // -*- Resets the state for starting/stopping time -*- //
    private fun resetState() {
        _timerState.update {
            it.copy(
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

}

