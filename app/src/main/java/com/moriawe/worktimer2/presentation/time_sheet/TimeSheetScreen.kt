package com.moriawe.worktimer2.presentation.time_sheet

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moriawe.worktimer2.presentation.component.TimeCard

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TimeSheetScreen(
    state: TimeSheetState,
    modifier: Modifier = Modifier,
    onOpenDialog: (Int) -> Unit
) {

    // -*- state to check if a day is expanded or not -*- //
    var expandedStates by remember { mutableStateOf(mapOf<String, Boolean>()) }


    Column(modifier = Modifier.fillMaxSize()) {

        // -*- Scrollable Column with all timeItems -*- //
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            state.months.forEach { month ->
                stickyHeader {
                    MonthHeader(month.name, month.totalWorkTimeInHours)
                }
                items(month.days) { day ->
                    val isExpanded = expandedStates[day.date] ?: false
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                expandedStates = expandedStates
                                    .toMutableMap()
                                    .apply {
                                        this[day.date] = !isExpanded
                                    }
                            }
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = day.date,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(text = day.totalWorkTime)
                    }
                    if (isExpanded) {
                        Column(
                            modifier = Modifier.padding(horizontal = 10.dp)
                        ) {
                            day.items.forEach { timeCardItem ->
                                TimeCard(
                                    time = timeCardItem,
                                    onClick = { onOpenDialog(timeCardItem.id) }
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MonthHeader(
    title: String,
    workTime: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
        Text(text = workTime)
    }
}
