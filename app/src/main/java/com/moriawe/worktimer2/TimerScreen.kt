package com.moriawe.worktimer2

import android.util.Log
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TimerScreen(modifier: Modifier = Modifier) {
    val viewModel: TimerViewModel = viewModel()
    var isStarted by remember { mutableStateOf(false) }

    val showDialog =  remember { mutableStateOf(false) }

    if(showDialog.value)
        CustomDialog(value = "", setShowDialog = {
            showDialog.value = it
        }) {
            Log.i("HomePage","HomePage : $it")
        }


    // Parent column
    Column(
        modifier = modifier
    ) {
        // Scrollable column that shows the timecards
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            for (time in viewModel.timeList) {
                TimeCard(
                    time = time,
                    onClick = {
                        showDialog.value = true
                        Log.d("TimerScreen", "TimeCard pressed")
                    }
                )
            }
        }
        // Bottom row start/stop button
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.Bottom
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