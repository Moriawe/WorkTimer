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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.moriawe.worktimer2.data.entity.TimeItem
import com.moriawe.worktimer2.domain.util.mapTimeItemToTimeCardItem

@Composable
fun TimeCard(timeItem: TimeItem, onClick: () -> Unit) {

    val time = mapTimeItemToTimeCardItem(timeItem)

    // -*- Time Card -*- //
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
            .padding(top = 15.dp)
            .shadow(3.dp)
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(10.dp)
            .clickable {
                onClick()
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(time.startTime)
            Text(" - ")
            Text(time.endTime)
        }
        Text(
            modifier = Modifier
                .weight(1f, fill = false)
                .padding(horizontal = 5.dp),
            text = time.description,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
        Text(time.totalTime)
    }
}

/*
Row(
            modifier = Modifier
                .size(75.dp)
                .padding(5.dp),
            //.clip(CircleShape)
            //.background(Color.Red),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

        }
 */