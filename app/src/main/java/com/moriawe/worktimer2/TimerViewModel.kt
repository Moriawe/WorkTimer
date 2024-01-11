package com.moriawe.worktimer2

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TimerViewModel(): ViewModel() {

    val timeList2 = mutableStateListOf<TimeItem2>()

    var totalTimeInSeconds: Long = 0
    var totalTimeInDuration: Duration? = null
    private var startTime: LocalDateTime? = null
    private var stopTime: LocalDateTime? = null

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")


    fun startTimer() {
        startTime = LocalDateTime.now()
    }

    fun stopTimer() {
        stopTime = LocalDateTime.now()
        calculateWorkTime()
    }

    fun calculateWorkTime() {
        // Calculating the amount of time between start and stop
        val time = Duration.between(startTime, stopTime)
        // Storing duration
        totalTimeInDuration?.plus(time)

        // Converting duration to hours, minutes and seconds.
        val seconds = time.toSecondsPart()
        val minutes = time.toMinutesPart()
        val hours = time.toHoursPart()

        Log.d("TimerViewModel", "$time $hours:$minutes:$seconds")
        Log.d("TimerViewModel", "$totalTimeInDuration")
        Log.d("TimerViewModel", "$totalTimeInSeconds")

        // Adding start and stop to list
        timeList2.add(TimeItem2(
            startTime,
            stopTime,
            "$hours:$minutes:$seconds",
            time.toSeconds(),
            time,
            "Test string"))
    }
}
