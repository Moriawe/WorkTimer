package com.moriawe.worktimer2.presentation.time_sheet

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.moriawe.worktimer2.presentation.MainViewModel
import com.moriawe.worktimer2.presentation.component.TimeCard

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TimeSheetScreen(
    viewModel: MainViewModel = viewModel(),
) {

    val state by viewModel.timeSheetState.collectAsState()
    var isExpanded by remember { mutableStateOf(false)}

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
                    DayHeader(
                        title = day.date,
                        workTime = day.totalWorkTime,
                        onClick = { isExpanded = !isExpanded})
                    if (isExpanded) {
                        Log.d("TIME SHEET SCREEN", "IsExpanded: $isExpanded")
                        LazyColumn(
                        ) {
                            items(day.items) { timeCardItem ->
                                TimeCard(time = timeCardItem) {
                                }
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

@Composable
private fun DayHeader(
    title: String,
    workTime: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick }
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        )
        Text(text = workTime)
    }
}