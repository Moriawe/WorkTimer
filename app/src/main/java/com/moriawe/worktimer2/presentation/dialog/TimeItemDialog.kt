package com.moriawe.worktimer2.presentation.dialog

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.moriawe.worktimer2.R

@Composable
fun TimeItemDialog(
    viewModel: DialogViewModel,
    onHideDialog: () -> Unit
) {

    // Init state and onEvent inside the compose
    // Can also be done in the NavGraph instead, like TimerScreen
    val state by viewModel.dialogState.collectAsState()
    val onEvent = viewModel::onEvent

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .clip(RoundedCornerShape(15.dp))
            .padding(20.dp)
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
                    value = state.startTime,
                    onValueChange = { onEvent(DialogEvent.SetStartTime(it)) },
                    label = { Text(stringResource(id = R.string.start_time)) },
                    isError = state.startTimeError != null
                )
                Spacer(modifier = Modifier.width(15.dp))
                OutlinedTextField(
                    modifier = Modifier
                        .weight(1f),
                    value = state.stopTime,
                    onValueChange = { onEvent(DialogEvent.SetStopTime(it)) },
                    label = { Text(stringResource(id = R.string.end_time)) },
                    isError = state.stopTimeError != null
                )
            }
            if (state.stopTimeError != null) {
                Text(
                    text = stringResource(id = state.stopTimeError!!),
                    color = MaterialTheme.colorScheme.error
                )
            }
            Spacer(modifier = Modifier.width(5.dp))
            OutlinedTextField(
                value = state.description,
                onValueChange = { onEvent(DialogEvent.SetDescription(it)) },
                label = { Text(stringResource(id = R.string.work_description)) }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalArrangement = Arrangement.End
            ) {
                ClickableText(
                    text = AnnotatedString(stringResource(id = R.string.cancel)),
                    onClick = { onHideDialog() })
                Spacer(modifier = Modifier.width(20.dp))
                ClickableText(
                    text = AnnotatedString(stringResource(id = R.string.save)),
                    onClick = {
                        onEvent(DialogEvent.UpdateTimeItem { success ->
                            if (success) {
                                Log.d("AddDescriptionDialog", "Save item: ${state.description}")
                                onHideDialog()
                            } else {
                                Log.d("AddDescriptionDialog", "Hide keyboard")
                            }
                        })
                    })
            }
        }

    }
}


