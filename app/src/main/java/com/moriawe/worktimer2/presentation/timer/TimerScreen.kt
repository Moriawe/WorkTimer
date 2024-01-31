package com.moriawe.worktimer2.presentation.timer

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissState
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moriawe.worktimer2.R
import com.moriawe.worktimer2.domain.mapper.mapTimeItemToTimeCardItem
import com.moriawe.worktimer2.presentation.component.TimeCard

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun TimerScreen(
    state: TimerState,
    onEvent: (TimerEvent) -> Unit,
    onOpenDialog: (Int) -> Unit
) {

    Column() {

        // -*- Total work time Row -*- //
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
                //.weight(1f),
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
                .weight(1f)
        ) {
            items(
                items = state.timeItems,
                key = { item -> item.id },
            ) { item ->
                // makes sure the correct item is dismissed, key
                val currentItem by rememberUpdatedState(newValue = item.id)
                val dismissState = rememberDismissState(
                    // What will happen when item is dismissed
                    confirmValueChange = {
                        if (it == DismissValue.DismissedToStart) {
                            onEvent(TimerEvent.DeleteTimeItem(currentItem))
                        }
                        true
                    },
                    // Decides how far the user has to swipe to dismiss item
                    positionalThreshold = { totalDistance ->
                        totalDistance * 0.60f
                    }
                )
                SwipeToDismiss(
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                        .padding(top = 15.dp)
                        .animateItemPlacement(),
                    state = dismissState,
                    directions = setOf(DismissDirection.EndToStart),
                    background = {
                        SwipeBackground(dismissState)
                    },
                    dismissContent = {
                        TimeCard(
                            time = mapTimeItemToTimeCardItem(item),
                            onClick = { onOpenDialog(item.id) }
                        )
                    },
                )
            }
        }

        // -*- Bottom row start/stop button -*- //
        Row(
            modifier = Modifier
                .fillMaxWidth(),
                //.weight(1f),
            verticalAlignment = Alignment.Bottom
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

// -*- The swipe to dismiss handling -*- //
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeBackground(dismissState: DismissState) {

    val color by animateColorAsState(
        if (dismissState.targetValue == DismissValue.DismissedToStart) Color.Red
        else Color.LightGray,
        label = ""
    )
    val scale by animateFloatAsState(
        if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f,
        label = ""
    )

    Box(
        Modifier
            .fillMaxSize()
            .background(color)
            .padding(horizontal = 20.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            Icons.Default.Delete,
            contentDescription = "Delete",
            modifier = Modifier.scale(scale)
        )
    }
}
