package com.jesuskrastev.watodo.ui.features.login

import com.jesuskrastev.watodo.utilities.error_handling.InformationUiState

data class LoginState(
    val email: String = "",
    val password: String = "",
    val information: InformationUiState = InformationUiState.Hidden(),
)
