package com.moriawe.worktimer2.presentation.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
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
import com.moriawe.worktimer2.domain.model.TimeItem
import java.time.format.DateTimeFormatter

@Composable
fun TimeCard(time: TimeItem, onValueChange: (String) -> Unit) {

    // TODO move out the formatters
    val dateFormatter = DateTimeFormatter.ofPattern("dd MMM")
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    val date = time.startTime?.format(dateFormatter)
    val startTime = time.startTime?.format(timeFormatter)
    val endTime = time.stopTime?.format(timeFormatter)
    val hours = time.totalTimeInDuration.toHoursPart()
    val minutes = time.totalTimeInDuration.toMinutesPart()
    val totalTimeString = "${hours}h ${minutes}m"

    var workDescription by remember { mutableStateOf(time.description) }

    // -*- Dialog -*- //
    val showDialog =  remember { mutableStateOf(false) }

    if (showDialog.value)
        EditTimeCardDialog(
            value = workDescription,
            timeItem = time,
            setShowDialog = { showDialog.value = it },
        ) { description ->
            // Changing state
            workDescription = description
            // sending state back to TimerScreen for update
            onValueChange(workDescription)
            Log.d("Dialog returnvalue", description)
        }

    // -*- Time Card -*- //
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .border(1.dp, Color.Black, shape = RectangleShape)
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .clickable {
                showDialog.value = true
            },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .size(75.dp)
                .padding(5.dp),
            //.clip(CircleShape)
            //.background(Color.Red),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("$date")
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(5.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text("$startTime - ")
                Text("$endTime")
            }
            Text(time.description)
        }
        Row(
            modifier = Modifier
                .size(75.dp)
                .padding(5.dp),
            //.clip(CircleShape)
            //.background(Color.Red),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(totalTimeString)
        }
    }
}