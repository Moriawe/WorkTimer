package com.moriawe.worktimer2.presentation.dialog

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.moriawe.worktimer2.R
import com.moriawe.worktimer2.presentation.MainViewModel
import com.moriawe.worktimer2.presentation.UiEvent
import com.moriawe.worktimer2.presentation.timer.TimerEvent
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ModifyTimeItemDialog(
    state: DialogState,
    onEvent: (TimerEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        modifier = modifier,
        title = {
            Text(text = "Update:")
        },
        text = {
            Column(
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .weight(1f),
                        value = state.startTime,
                        onValueChange = { onEvent(TimerEvent.SetStartTime(it)) },
                        label = { Text(stringResource(id = R.string.start_time) ) }
                    )
                    OutlinedTextField(
                        modifier = Modifier
                            .weight(1f),
                        value = state.stopTime,
                        onValueChange = { onEvent(TimerEvent.SetStopTime(it)) },
                        label = { Text(stringResource(id = R.string.end_time) ) }
                    )
                }
                OutlinedTextField(
                    value = state.description,
                    onValueChange = { onEvent(TimerEvent.SetDescription(it)) },
                    label = { Text(stringResource(id = R.string.work_description) ) }
                )
            }
        },
        onDismissRequest = {
            onEvent(TimerEvent.HideDialog)
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onEvent(TimerEvent.UpdateTimeItem)
                    Log.d("AddDescriptionDialog", "Save item: ${state.description}")
                    onEvent(TimerEvent.HideDialog)
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onEvent(TimerEvent.HideDialog)
                }
            ) {
                Text("Cancel")
            }
        }
    )
}
