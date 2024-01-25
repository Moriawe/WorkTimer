package com.moriawe.worktimer2.presentation.dialog

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.moriawe.worktimer2.R
import com.moriawe.worktimer2.presentation.timer.TimerEvent

@OptIn(ExperimentalComposeUiApi::class)
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                ) {
                        OutlinedTextField(
                            modifier = Modifier
                                .weight(1f),
                            value = state.startTime,
                            onValueChange = { onEvent(TimerEvent.SetStartTime(it)) },
                            label = { Text(stringResource(id = R.string.start_time) ) },
                            isError = state.startTimeError != null
                        )
                        OutlinedTextField(
                            modifier = Modifier
                                .weight(1f),
                            value = state.stopTime,
                            onValueChange = { onEvent(TimerEvent.SetStopTime(it)) },
                            label = { Text(stringResource(id = R.string.end_time) ) },
                            isError = state.stopTimeError != null
                        )
                }
                if(state.stopTimeError != null) {
                    Text(
                        text = stringResource(id = state.stopTimeError),
                        color = MaterialTheme.colorScheme.error
                    )
                }
                Spacer(modifier = Modifier.width(5.dp))
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
                    onEvent(TimerEvent.UpdateTimeItem { success ->
                     if (success) {
                         Log.d("AddDescriptionDialog", "Save item: ${state.description}")
                         onEvent(TimerEvent.HideDialog)
                     } else {
                         Log.d("AddDescriptionDialog", "Hide keyboard")
                     }
                    })
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
