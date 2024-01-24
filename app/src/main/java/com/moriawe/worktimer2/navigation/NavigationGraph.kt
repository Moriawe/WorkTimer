package com.moriawe.worktimer2.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.moriawe.worktimer2.presentation.MainViewModel
import com.moriawe.worktimer2.presentation.modifier.ModifierScreen
import com.moriawe.worktimer2.presentation.time_sheet.TimeSheetScreen
import com.moriawe.worktimer2.presentation.timer.TimerScreen


@Composable
fun NavigationGraph(navController: NavHostController, innerPadding: PaddingValues) {

    val viewModel = hiltViewModel<MainViewModel>()

    NavHost(
        navController = navController,
        startDestination = Screen.Timer.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(route = Screen.Timer.route) {
            val state by viewModel.timerState.collectAsState()
            val dialogState by viewModel.dialogState.collectAsState()
            val onEvent = viewModel::onEvent
            TimerScreen(viewModel, state, dialogState, onEvent)
        }
        composable(route = Screen.TimeSheet.route) {
            val state by viewModel.timeSheetState.collectAsState()
            TimeSheetScreen(state)
        }
        composable(route = Screen.Modifier.route) {
            val dialogState by viewModel.dialogState.collectAsState()
            val onEvent = viewModel::onEvent
            ModifierScreen(dialogState, onEvent)
        }
    }
}