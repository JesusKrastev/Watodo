package com.jesuskrastev.watodo.ui.features.login

import com.google.android.gms.auth.api.signin.GoogleSignInClient

sealed interface LoginEvent {
    data class OnEmailChanged(val email: String) : LoginEvent
    data class OnPasswordChanged(val password: String) : LoginEvent
    data class OnLoginWithGoogle(
        val idToken: String,
        val onRestartApp: () -> Unit,
    ): LoginEvent
    data class OnGoogleLoginSelected(val googleLauncher: (GoogleSignInClient) -> Unit): LoginEvent
    data class OnLogin(val onRestartApp: () -> Unit): LoginEvent
}