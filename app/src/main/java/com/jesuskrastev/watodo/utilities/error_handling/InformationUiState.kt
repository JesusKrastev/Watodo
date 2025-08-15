package com.jesuskrastev.watodo.utilities.error_handling

sealed class InformationUiState(val visible: Boolean, val message: String = "") {
    class Hidden :
        InformationUiState(
            visible = false
        )

    class Information(message: String, val showProgress: Boolean = false) :
        InformationUiState(
            visible = true,
            message = message
        )

    class Error(message: String, val onDismiss: () -> Unit) :
        InformationUiState(
            visible = true,
            message = message
        )

    class Success(message: String) :
        InformationUiState(
            visible = true,
            message = message
        )
}
