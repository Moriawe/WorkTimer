package com.moriawe.worktimer2.presentation.timer

import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.moriawe.worktimer2.data.entity.TimeItem
import com.moriawe.worktimer2.domain.util.TimeFormatters.dateFormatter
import com.moriawe.worktimer2.domain.util.TimeFormatters.timeFormatter

@Composable
fun TimeCard(time: TimeItem, onClick: () -> Unit) {

    val date = time.startTime.format(dateFormatter)
    val startTime = time.startTime.format(timeFormatter)
    val endTime = time.stopTime.format(timeFormatter)
    //val hours = time.totalTimeInDuration.toHoursPart()
    //val minutes = time.totalTimeInDuration.toMinutesPart()
    //val totalTimeString = "${hours}h ${minutes}m"

    // -*- Time Card -*- //
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .padding(top = 10.dp)
            .shadow(3.dp)
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .clickable {
                onClick()
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
            //Text(totalTimeString)
        }
    }
}