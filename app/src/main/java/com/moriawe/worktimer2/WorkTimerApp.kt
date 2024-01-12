package com.moriawe.worktimer2

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.moriawe.worktimer2.navigation.NavigationGraph
import com.moriawe.worktimer2.navigation.Screen
import com.moriawe.worktimer2.presentation.TimerAppBar

@Composable
fun WorkTimerApp(
    navController: NavHostController
) {

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = Screen.valueOf(
        backStackEntry?.destination?.route ?: Screen.Timer.name )

    Scaffold(
        topBar = {
            TimerAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navController = navController,
                navigateUp = { navController.navigateUp() })
        }
    ) { innerPadding ->
        NavigationGraph(navController, innerPadding)
    }

}