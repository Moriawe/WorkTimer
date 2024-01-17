package com.moriawe.worktimer2.presentation.timer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.moriawe.worktimer2.R
import com.moriawe.worktimer2.presentation.components.AddDescriptionDialog
import com.moriawe.worktimer2.presentation.components.TimeCard


@Composable
fun TimerScreen(
    timerViewModel: TimerViewModel = viewModel(),
    //state: TimerState = timerViewModel.state,
    //onEvent: (TimerEvent) -> Unit
) {

    val state by timerViewModel.state.collectAsState()
    val onEvent = timerViewModel::onEvent

    // -*- Dialog -*- //
    if (state.isModifyingTimeCard)
        AddDescriptionDialog(state = state, onEvent = onEvent)

    // -*- Parent column -*- //
    Column() {

        // -*- Scrollable column with Time Cards -*- //
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(state.timeItems) { timeItem ->
                TimeCard(
                    time = timeItem,
                    onClick = { onEvent(TimerEvent.ShowDialog) }
                )
            }
        }

        // -*- Bottom row start/stop button -*- //
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Button(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                onClick = {
                    if (state.isTimerStarted) onEvent(TimerEvent.StopTimer)
                    else onEvent(TimerEvent.StartTimer)
                }
            ) {
                Text(
                    fontSize = 20.sp,
                    text = if (state.isTimerStarted) stringResource(id = R.string.stop)
                    else stringResource(id = R.string.start)
                )
            }
        }
    }

}