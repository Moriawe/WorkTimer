package com.moriawe.worktimer2.presentation.time_sheet

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.moriawe.worktimer2.domain.model.Day
import com.moriawe.worktimer2.domain.model.Month
import com.moriawe.worktimer2.domain.model.TimeCardItem

@Composable
fun TimeSheetScreen(
    viewModel: TimeSheetViewModel = viewModel(),
) {

    val state by viewModel.state.collectAsState()

    // -*- Parent column -*- //
    Column(modifier = Modifier.fillMaxSize()) {
        OverViewListColumn(months = state.months)
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
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        )
        Text(text = workTime)
    }
}

@Composable
private fun OverviewTimeCardItem(
    day: Day,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = day.date)
        Text(text = day.totalWorkTime)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun OverViewListColumn(
    months: List<Month>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        months.forEach { month ->
            stickyHeader {
                MonthHeader(month.name, month.totalWorkTimeInHours)
            }
            items(month.days) { day ->
                OverviewTimeCardItem(day)
            }
        }
    }
}