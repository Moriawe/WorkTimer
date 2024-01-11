package com.moriawe.worktimer2

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.time.format.DateTimeFormatter

@Composable
fun TimerScreen(modifier: Modifier = Modifier) {
    val viewModel: TimerViewModel = viewModel()
    var isRunning by remember { mutableStateOf(false) }

    val dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM HH:mm:ss")
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")

    Column(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            for (time in viewModel.timeList2) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(5.dp)
                        .border(1.dp, Color.Black, shape = RectangleShape)
                        .padding(5.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        /*
                            var timeColor = if (time.isStartTime) Color.Green else Color.Red
                            Text(
                                text = time.timeAsText,
                                color = timeColor
                            )
                         */
                        Text("${time.startTime?.format(dateTimeFormatter)} - ")
                        //Spacer(modifier = Modifier.width(4.dp))
                        Text("${time.endTime?.format(timeFormatter)}")
                        val seconds = time.totalTimeInDuration.toSecondsPart()
                        val minutes = time.totalTimeInDuration.toMinutesPart()
                        val hours = time.totalTimeInDuration.toHoursPart()
                        Text("Total Time: $hours:$minutes:$seconds")
                        //Text("Total Time: ${time.totalTimeInSeconds}")

                    }
                    //Spacer(modifier = Modifier.height(5.dp))
                    Text("Something you did")

                }
            }
        }
        BottomRowButton()
    }
}

@Composable
fun BottomRowButton(onClick: () -> Unit) {
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
                if (isRunning) viewModel.stopTimer() else viewModel.startTimer()
                isRunning = !isRunning
            }
        ) {
            Text(
                fontSize = 20.sp,
                text = if (isRunning) "Stop" else "Start"
            )
        }
    }
}