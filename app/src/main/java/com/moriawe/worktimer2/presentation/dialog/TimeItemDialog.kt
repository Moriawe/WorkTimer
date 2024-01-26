package com.moriawe.worktimer2.presentation.dialog

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.moriawe.worktimer2.R
import com.moriawe.worktimer2.presentation.MainViewModel
import com.moriawe.worktimer2.presentation.timer.TimerEvent

@Composable
fun TimeItemDialog(
    timeItemId: Int? = null,
    viewModel: MainViewModel
) {

    val state by viewModel.dialogState.collectAsState()
    val onEvent = viewModel::onEvent

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(10.dp)
            .clip(RoundedCornerShape(5.dp))
    ) {
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
                    value = timeItemId.toString(),
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
                    text = stringResource(id = state.stopTimeError!!),
                    color = MaterialTheme.colorScheme.error
                )
            }
            Spacer(modifier = Modifier.width(5.dp))
            OutlinedTextField(
                value = state.description,
                onValueChange = { onEvent(TimerEvent.SetDescription(it)) },
                label = { Text(stringResource(id = R.string.work_description) ) }
            )
            Row() {
                Button(onClick = { onEvent(TimerEvent.HideDialog) }) {
                    Text(text = stringResource(id = R.string.cancel))
                }
                Button(onClick = {
                    onEvent(TimerEvent.UpdateTimeItem { success ->
                        if (success) {
                            Log.d("AddDescriptionDialog", "Save item: ${state.description}")
                            onEvent(TimerEvent.HideDialog)
                        } else {
                            Log.d("AddDescriptionDialog", "Hide keyboard")
                        }
                    })
                }) {
                    Text(text = stringResource(id = R.string.save))
                }
            }
        }
    }

}


