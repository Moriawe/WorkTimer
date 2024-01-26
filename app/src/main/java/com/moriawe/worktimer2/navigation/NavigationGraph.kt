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
import androidx.navigation.NavArgument
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navArgument
import com.moriawe.worktimer2.presentation.MainViewModel
import com.moriawe.worktimer2.presentation.UiEvent
import com.moriawe.worktimer2.presentation.dialog.TimeItemDialog
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
        startDestination = Screen.TimerScreen.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(route = Screen.TimerScreen.route) {
            val state by viewModel.timerState.collectAsState()
            //val dialogState by viewModel.dialogState.collectAsState()
            val onEvent = viewModel::onEvent
            TimerScreen(state, onEvent, onOpenDialog = {
                navController.navigate(Screen.DialogScreen.withArgs(it))
            })
        }
        composable(route = Screen.TimeSheetScreen.route) {
            val state by viewModel.timeSheetState.collectAsState()
            TimeSheetScreen(state)
        }
        // -*- Calling dialog from NavGraph with argument for timeItemId -*- //
        // -*- It is strongly advised not to pass around complex data objects when navigating -*- //
        dialog(
            route = Screen.DialogScreen.route + "/{timeItemId}",
            arguments = listOf(
                navArgument("timeItemId") {
                    type = NavType.IntType
                }
            )
        ) { entry ->
            //val dialogState by viewModel.dialogState.collectAsState()
            //val onEvent = viewModel::onEvent
            TimeItemDialog(
                timeItemId = entry.arguments?.getInt("timeItemId"),
                viewModel)
            //ModifyTimeItemDialog(dialogState, onEvent)
        }
    }
}