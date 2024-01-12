package com.moriawe.worktimer2.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.moriawe.worktimer2.domain.model.TimeCardItem

@Composable
fun TimeSheetScreen() {

    val viewModel: TimeSheetViewModel = viewModel()

    // -*- Parent column -*- //
    Column(modifier = Modifier.fillMaxSize()) {
        OverViewListColumn(months = viewModel.overViewList )
    }

}

@Composable
private fun MonthHeader(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp)
    )
}

@Composable
private fun OverviewTimeItem(
    timeCardItem: TimeCardItem,
    modifier: Modifier = Modifier
) {
    Text(
        text = timeCardItem.startTime,
        fontSize = 14.sp,
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun OverViewListColumn(
    months: List<Month>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        months.forEach { month ->
            stickyHeader {
                MonthHeader(month.name)
            }
            items(month.items) { timeCardItem ->
                OverviewTimeItem(timeCardItem)
            }
        }
    }
}