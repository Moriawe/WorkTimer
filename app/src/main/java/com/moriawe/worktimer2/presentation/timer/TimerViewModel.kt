package com.moriawe.worktimer2.presentation.timer

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moriawe.worktimer2.data.TimeDao
import com.moriawe.worktimer2.data.entity.TimeItem2
import com.moriawe.worktimer2.domain.model.TimeItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDateTime

class TimerViewModel(
    private val dao: TimeDao
): ViewModel() {

    private val _timeItems = dao.getTimeItems()
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
            is TimerEvent.DeleteTime -> {
                viewModelScope.launch {
                    dao.deleteTimeItem(event.timeItem2)
                }
            }
            TimerEvent.HideDialog -> {
                _state.update { it.copy(
                    isModifyingTimeCard = false
                ) }
            }
            TimerEvent.SaveTimeItem -> {
                val startTime = state.value.startTime
                val stopTime = state.value.stopTime
                val description = state.value.description

                // Null Check
                if (startTime == null || stopTime == null) { return }

                // Save time item to database
                val timeItem = TimeItem2(
                    startTime = startTime,
                    stopTime = stopTime,
                    description = description
                )
                viewModelScope.launch {
                    dao.insertTimeItem(timeItem2 = timeItem)
                }
                // Reset the state
                _state.update { it.copy(
                    isModifyingTimeCard = false,
                    startTime = null,
                    stopTime = null,
                    description = ""
                )}

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
            TimerEvent.ShowDialog -> {
                _state.update { it.copy(
                    isModifyingTimeCard = true
                ) }
            }
        }
    }

    // OLD

    val timeList = mutableStateListOf<TimeItem>()

    private var totalTimeInDuration: Duration? = null
    private var startTime: LocalDateTime? = null
    private var stopTime: LocalDateTime? = null

    fun startTimer() {
        startTime = LocalDateTime.now()
    }

    fun stopTimer() {
        stopTime = LocalDateTime.now()
        calculateWorkTime()
    }

    fun calculateWorkTime() {
        // Calculating the amount of time between start and stop
        val totalTime = Duration.between(startTime, stopTime)
        // Storing duration
        totalTimeInDuration?.plus(totalTime)

            timeList.add(
                TimeItem(
                    startTime = startTime ?: LocalDateTime.of(2023, 1, 1, 1, 1, 1),
                    stopTime = stopTime ?: LocalDateTime.of(2023, 1, 1, 1, 1, 1),
                    totalTimeInDuration = totalTime,
                    description = "")
            )
        // Adding item to list

    }
    fun changeDescription(description: String) {
        // TODO
    }
}
