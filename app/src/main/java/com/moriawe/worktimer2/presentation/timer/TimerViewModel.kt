package com.moriawe.worktimer2.presentation.timer

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moriawe.worktimer2.data.TimeRepository
import com.moriawe.worktimer2.data.entity.TimeItem2
import com.moriawe.worktimer2.domain.use_case.GetTimeItemsByTodayUseCase
import com.moriawe.worktimer2.domain.use_case.GetTimeItemsByTodayUseCase_Factory
import com.moriawe.worktimer2.domain.use_case.GetTimeItemsSortedByDateUseCase
import com.moriawe.worktimer2.domain.util.TimeConstant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
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
): ViewModel() {

    private val _timeItems = getTimeItemsByTodayUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    private val _state = MutableStateFlow(TimerState())

    // This updates the state whenever there is a change in either _state or _timeItems
    val state = combine(_state, _timeItems) { state, timeItems ->
        state.copy(
            timeItems = timeItems
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TimerState())

    fun onEvent(event: TimerEvent) {
        when(event) {
            TimerEvent.SaveTimeItem -> {
                saveTimeItem()
            }
            is TimerEvent.SetDescription -> {
                _state.update { it.copy(
                    description = event.description
                )  }
            }
            is TimerEvent.SetStartTime -> {
                _state.update { it.copy(
                    startTime = event.startTime
                )  }
            }
            is TimerEvent.SetStopTime -> {
                _state.update { it.copy(
                    stopTime = event.stopTime
                )  }
            }
            TimerEvent.StartTimer -> {
                _state.update { it.copy(
                    startTime = LocalDateTime.now(),
                    isTimerStarted = true
                ) }
            }
            TimerEvent.StopTimer -> {
                _state.update { it.copy(
                    stopTime = LocalDateTime.now(),
                    isTimerStarted = false,
                ) }
                saveTimeItem()
            }
            TimerEvent.ShowDialog -> {
                _state.update { it.copy(
                    isModifyingTimeCard = true
                ) }
            }
            TimerEvent.HideDialog -> {
                _state.update { it.copy(
                    isModifyingTimeCard = false
                ) }
            }
        }
    }

    private fun saveTimeItem() {
        val startTime = state.value.startTime
        val stopTime = state.value.stopTime
        val description = state.value.description
        Log.d("TimerViewModel", "Start: $startTime, Stop: $stopTime, Desc: $description")
        // Null Check - CHANGE
        if (
            startTime == LocalDateTime.parse(
                TimeConstant.TIME_DEFAULT_STRING
            )
            || stopTime == LocalDateTime.parse(
                TimeConstant.TIME_DEFAULT_STRING
            )
        ) {
            return
        }

        // Save time item to database
        val timeItem = TimeItem2(
            startTime = startTime,
            stopTime = stopTime,
            description = description
        )
        viewModelScope.launch {
            Log.d("TimerViewModel", "Sent to Repo $timeItem")
            repo.insertTimeItem(timeItem2 = timeItem)
        }
        // Reset the state
        _state.update {
            it.copy(
                isModifyingTimeCard = false,
                startTime = LocalDateTime.parse(TimeConstant.TIME_DEFAULT_STRING),
                stopTime = LocalDateTime.parse(TimeConstant.TIME_DEFAULT_STRING),
                description = ""
            )
        }
    }
}
