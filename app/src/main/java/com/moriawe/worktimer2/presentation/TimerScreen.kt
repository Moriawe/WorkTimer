package com.moriawe.worktimer2.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.moriawe.worktimer2.presentation.components.TimeCard

@Composable
fun TimerScreen() {

    val viewModel: TimerViewModel = viewModel()
    var isStarted by remember { mutableStateOf(false) }

    // Parent column
    Column() {
        // Scrollable column that shows the timecards
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            for (time in viewModel.timeList) {
                TimeCard(
                    time = time
                )
            }
        }
        // Bottom row start/stop button
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Button(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                onClick = {
                    if (isStarted) viewModel.stopTimer() else viewModel.startTimer()
                    isStarted = !isStarted
                }
            ) {
                Text(
                    fontSize = 20.sp,
                    text = if (isStarted) "Stop" else "Start"
                )
            }
        }
    }

}