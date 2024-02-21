package com.moriawe.worktimer2.presentation

import android.content.ActivityNotFoundException
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.moriawe.worktimer2.domain.util.CsvExporter
import com.moriawe.worktimer2.navigation.NavigationGraph
import com.moriawe.worktimer2.navigation.Screen
import com.moriawe.worktimer2.ui.theme.WorkTimer2Theme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val backStackEntry by navController.currentBackStackEntryAsState()
            val currentScreen = Screen.fromRouteString(backStackEntry?.destination?.route)
//            When using enum class
//            val currentScreen = Screen.valueOf(
//                backStackEntry?.destination?.route ?: Screen.Timer.route )
            val snackbarHostState = remember { SnackbarHostState() }
            val mainViewModel = hiltViewModel<MainViewModel>()
            val onEvent = mainViewModel::onEvent


            // TODO: Test if snackbars are seen on all screens. How to make a better snackbar handling?
            // -*- SNACKBAR CONFIG -*- //
            val context = LocalContext.current

            LaunchedEffect(key1 = true) {
                mainViewModel.eventFlow.collectLatest { event ->
                    when (event) {
                        is UiEvent.ShowSnackbar -> {
                            snackbarHostState.showSnackbar(
                                message = context.resources.getString(event.message)
                            )
                        }
                    }
                }
            }

            // -*- INTENT CONFIG -*- //
            mainViewModel.exportIntent.observe(this) { intent ->
                intent?.let {
                    startActivity(intent)
                }
            }


            WorkTimer2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            TimerAppBar(
                                currentScreen = currentScreen,
                                canNavigateBack = navController.previousBackStackEntry != null,
                                navigateUp = { navController.navigateUp() },
                                downloadCsv = {
                                    onEvent(MainEvent.ExportToCSV)
                                },
                                navController = navController,
                            )
                        },
                        snackbarHost = {
                            SnackbarHost(hostState = snackbarHostState)
                        }
                    ) { innerPadding ->
                        NavigationGraph(navController, snackbarHostState, innerPadding)
                    }
                }
            }
        }
    }
}