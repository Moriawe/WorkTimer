package com.moriawe.worktimer2.presentation

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.moriawe.worktimer2.domain.TimeItem
import java.time.Duration
import java.time.LocalDateTime

class TimerViewModel(): ViewModel() {

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

        // Adding item to list
        timeList.add(
            TimeItem(
            startTime = startTime,
            endTime = stopTime,
            totalTimeInDuration = totalTime,
            description = "")
        )
    }
}
