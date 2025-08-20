package com.jesuskrastev.watodo.ui.navigation

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jesuskrastev.watodo.ui.features.webs.WebsScreen
import com.jesuskrastev.watodo.ui.features.webs.WebsState
import com.jesuskrastev.watodo.ui.features.webs.WebsViewModel
import kotlinx.serialization.Serializable

@Serializable
object WebsRoute : Destination

fun NavGraphBuilder.websScreen(
    vm: WebsViewModel,
) {
    composable<WebsRoute> {
        val state by vm.state.collectAsStateWithLifecycle(initialValue = WebsState())

        WebsScreen(
            state = state,
            onEvent = vm::onEvent,
        )
    }
}