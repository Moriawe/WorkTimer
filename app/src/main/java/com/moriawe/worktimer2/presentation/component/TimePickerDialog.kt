package com.moriawe.worktimer2.presentation.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import java.time.LocalDateTime

@Composable
fun TimePickerDialog(date: LocalDateTime) {


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTimePicker(date: LocalDateTime) {
    val timePicker = remember {
        TimePickerState(
            initialHour = date.hour,
            initialMinute = date.minute,
            is24Hour = true
        )
    }
    TimePicker(state = timePicker)
}