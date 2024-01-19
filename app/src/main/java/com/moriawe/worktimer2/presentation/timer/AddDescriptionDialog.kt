package com.moriawe.worktimer2.presentation.timer

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.moriawe.worktimer2.R
import com.moriawe.worktimer2.presentation.timer.TimerEvent
import com.moriawe.worktimer2.presentation.timer.TimerState

@Composable
fun AddDescriptionDialog(
    state: DialogState,
    onEvent: (TimerEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        modifier = modifier,
        title = {
            Text(text = "Add a work description:")
        },
        text = {
            Column() {
                // TODO : Find out how to use String resources here
                Text(text = "Between ${state.startTime} - ${state.stopTime}")
                OutlinedTextField(
                    value = state.description,
                    onValueChange = { onEvent(TimerEvent.SetDescription(it)) },
                    label = { Text(stringResource(id = R.string.work_description) ) })
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
