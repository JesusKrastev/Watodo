package com.jesuskrastev.watodo.ui.features.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.jesuskrastev.watodo.data.services.authentication.AuthServiceException
import com.jesuskrastev.watodo.data.services.authentication.AuthServiceImplementation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authService: AuthServiceImplementation
) : ViewModel() {
    private val _state = MutableStateFlow<LoginState>(LoginState())
    val state: StateFlow<LoginState> = _state

    fun onGoogleLoginSelected(googleLauncher: (GoogleSignInClient) -> Unit) {
        val gsc = authService.getGoogleClient()
        googleLauncher(gsc)
    }

    private fun loginWithGoogle(
        idToken: String,
        onNavigateToActivities: () -> Unit,
    ) {
        viewModelScope.launch {
            try {
                val result = authService.loginWithGoogle(idToken)

                if(result != null) onNavigateToActivities()
            } catch (e: AuthServiceException) {
//                informationState = InformationUiState.Error(
//                    message = UiText.StringResource(R.string.error_signing_in_with_google),
//                    onDismiss = { informationState = InformationUiState.Hidden() }
//                )
//                Log.d("LoginViewModel", "Error: ${e.message}")
            }
        }
    }

    private fun signIn(
        onNavigateToActivities: () -> Unit,
    ) {
        viewModelScope.launch {
//            informationState = InformationUiState.Information(
//                showProgress = true,
//                message = UiText.StringResource(R.string.signing_in),
//            )
            try {
                withContext(Dispatchers.IO) {
                    authService.signIn(_state.value.email, _state.value.password)
                }
//                informationState = InformationUiState.Hidden()
                onNavigateToActivities()
            } catch (e: AuthServiceException) {
//                informationState = InformationUiState.Error(
//                    message = UiText.StringResource(R.string.error_signing_in),
//                    onDismiss = { informationState = InformationUiState.Hidden() }
//                )
                Log.d("LoginViewModel", "Error: ${e.message}")
            }
        }
    }

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnEmailChanged -> {
                _state.value = _state.value.copy(email = event.email)
            }

            is LoginEvent.OnPasswordChanged -> {
                _state.value = _state.value.copy(password = event.password)
            }

            is LoginEvent.OnGoogleLoginSelected -> {
                onGoogleLoginSelected(event.googleLauncher)
            }

            is LoginEvent.OnLoginWithGoogle -> {
                loginWithGoogle(
                    idToken = event.idToken,
                    onNavigateToActivities = event.onNavigateToActivities,
                )
            }

            is LoginEvent.OnLogin -> {
                signIn(
                    onNavigateToActivities = event.onNavigateToActivities,
                )
            }
            else -> {}
        }
    }
}