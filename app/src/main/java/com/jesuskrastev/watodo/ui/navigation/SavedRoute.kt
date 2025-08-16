package com.jesuskrastev.watodo.ui.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jesuskrastev.watodo.ui.features.saved.SavedScreen
import com.jesuskrastev.watodo.ui.features.saved.SavedState
import com.jesuskrastev.watodo.ui.features.saved.SavedViewModel
import kotlinx.serialization.Serializable

@Serializable
object SavedRoute : Destination

fun NavGraphBuilder.savedScreen() {
    composable<SavedRoute> {
        val vm: SavedViewModel = hiltViewModel()
        val state by vm.state.collectAsStateWithLifecycle(initialValue = SavedState())

        SavedScreen(
            state = state,
            onEvent = vm::onEvent,
        )
    }
}