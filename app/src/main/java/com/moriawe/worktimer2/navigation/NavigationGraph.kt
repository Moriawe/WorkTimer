package com.moriawe.worktimer2.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.moriawe.worktimer2.presentation.time_sheet.TimeSheetScreen
import com.moriawe.worktimer2.presentation.timer.TimerScreen
import androidx.hilt.navigation.compose.hiltViewModel
import com.moriawe.worktimer2.presentation.timer.TimerViewModel


@Composable
fun NavigationGraph(navController: NavHostController, innerPadding: PaddingValues) {

    NavHost(
        navController = navController,
        startDestination = Screen.Timer.name,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(route = Screen.Timer.name) {
            val viewModel = hiltViewModel<TimerViewModel>()
            TimerScreen(viewModel)
        }
        composable(route = Screen.TimeSheet.name) {
            TimeSheetScreen()
        }
    }
}