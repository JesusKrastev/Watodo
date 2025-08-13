package com.jesuskrastev.watodo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jesuskrastev.watodo.ui.features.activities.ActivitiesScreen
import com.jesuskrastev.watodo.ui.features.activities.ActivitiesState
import com.jesuskrastev.watodo.ui.features.activities.ActivitiesViewModel
import com.jesuskrastev.watodo.ui.theme.WatodoTheme
import androidx.compose.runtime.getValue
import com.jesuskrastev.watodo.ui.features.components.NavBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WatodoTheme {
                val vm: ActivitiesViewModel = hiltViewModel()
                val state: ActivitiesState by vm.state.collectAsStateWithLifecycle(initialValue = ActivitiesState())

                Scaffold(
                    contentWindowInsets = WindowInsets.navigationBars,
                    bottomBar = {
                        NavBar()
                    }
                ) { paddingValues ->
                    ActivitiesScreen(
                        modifier = Modifier.padding(paddingValues),
                        state = state,
                        onEvent = vm::onEvent,
                    )
                }
            }
        }
    }
}