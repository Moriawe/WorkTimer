package com.moriawe.worktimer2.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.moriawe.worktimer2.presentation.TimeSheetScreen
import com.moriawe.worktimer2.presentation.TimerScreen

@Composable
fun NavigationGraph(navController: NavHostController, innerPadding: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = Screen.Timer.name,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(route = Screen.Timer.name) {
            TimerScreen()
        }
        composable(route = Screen.TimeSheet.name) {
            TimeSheetScreen()
        }
    }
}