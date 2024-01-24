package com.moriawe.worktimer2.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.moriawe.worktimer2.presentation.MainViewModel
import com.moriawe.worktimer2.presentation.UiEvent
import com.moriawe.worktimer2.presentation.dialog.ModifyTimeItemDialog
import com.moriawe.worktimer2.presentation.time_sheet.TimeSheetScreen
import com.moriawe.worktimer2.presentation.timer.TimerScreen
import kotlinx.coroutines.flow.collectLatest


@Composable
fun NavigationGraph(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    innerPadding: PaddingValues) {

    val viewModel = hiltViewModel<MainViewModel>()
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = context.resources.getString(event.message)
                    )
                }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Timer.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(route = Screen.Timer.route) {
            val state by viewModel.timerState.collectAsState()
            val dialogState by viewModel.dialogState.collectAsState()
            val onEvent = viewModel::onEvent
            TimerScreen(state, dialogState, onEvent)
        }
        composable(route = Screen.TimeSheet.route) {
            val state by viewModel.timeSheetState.collectAsState()
            TimeSheetScreen(state)
        }
        composable(route = Screen.Dialog.route) {
            val dialogState by viewModel.dialogState.collectAsState()
            val onEvent = viewModel::onEvent
            ModifyTimeItemDialog(dialogState, onEvent)
        }
    }
}