package com.jesuskrastev.watodo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jesuskrastev.watodo.ui.features.components.NavBar
import com.jesuskrastev.watodo.ui.navigation.LoginRoute
import com.jesuskrastev.watodo.ui.navigation.WatodoNavHost
import com.jesuskrastev.watodo.ui.theme.WatodoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WatodoTheme {
                val navController = rememberNavController()
                val navBackStackEntry = navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry.value?.destination

                Scaffold(
                    contentWindowInsets = WindowInsets.navigationBars,
                    bottomBar = {
                        if (currentRoute?.hasRoute(LoginRoute::class) == false)
                            NavBar(onNavigateTo = { navController.navigate(it) }  )
                    }
                ) { paddingValues ->
                    WatodoNavHost(
                        modifier = Modifier.padding(paddingValues),
                        navController = navController,
                    )
                }
            }
        }
    }
}