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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moriawe.worktimer2.R
import com.moriawe.worktimer2.domain.mapper.mapTimeItemToTimeCardItem
import com.moriawe.worktimer2.presentation.component.TimeCard

@Composable
fun TimerScreen(
    state: TimerState,
    onEvent: (TimerEvent) -> Unit,
    onOpenDialog: (Int) -> Unit
) {

    Column() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Total worktime for today: ${state.totalWorkTime}"
            )
        }

        // -*- Scrollable column with TimeCards -*- //
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
        ) {
            items(state.timeItems) { timeItem ->
                TimeCard(
                    time = mapTimeItemToTimeCardItem(timeItem),
                    onClick = { onOpenDialog(timeItem.id) }
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