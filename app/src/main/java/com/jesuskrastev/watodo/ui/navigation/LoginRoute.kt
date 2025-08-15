package com.jesuskrastev.watodo.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jesuskrastev.watodo.ui.features.login.LoginScreen
import com.jesuskrastev.watodo.ui.features.login.LoginState
import com.jesuskrastev.watodo.ui.features.login.LoginViewModel
import kotlinx.serialization.Serializable
import androidx.compose.runtime.getValue

@Serializable
object LoginRoute: Destination

fun NavGraphBuilder.loginScreen(
    onNavigateTo: (Destination) -> Unit,
) {
    composable<LoginRoute> {
        val vm: LoginViewModel = hiltViewModel()
        val state by vm.state.collectAsStateWithLifecycle(initialValue = LoginState())

        LoginScreen(
            state = state,
            onNavigateTo = onNavigateTo,
            onEvent = vm::onEvent,
        )
    }
}