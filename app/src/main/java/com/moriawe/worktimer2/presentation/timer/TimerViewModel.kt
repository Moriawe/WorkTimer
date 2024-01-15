package com.moriawe.worktimer2.presentation.timer

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moriawe.worktimer2.domain.model.TimeCardItem
import com.moriawe.worktimer2.domain.model.TimeItem
import java.time.Duration
import java.time.LocalDateTime

class TimerViewModel(): ViewModel() {

    val timeList = mutableStateListOf<TimeItem>()

    val formattedTimeList = mutableStateListOf<TimeCardItem>()
    // Can this be a list? List with live data? Differense between livedata and state?
    val _testList = MutableLiveData<TimeItem>()
    val testList: LiveData<TimeItem> = _testList

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
