package com.jesuskrastev.watodo.ui.features.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.jesuskrastev.watodo.data.UserRepository
import com.jesuskrastev.watodo.data.services.authentication.AuthServiceException
import com.jesuskrastev.watodo.data.services.authentication.AuthServiceImplementation
import com.jesuskrastev.watodo.models.User
import com.jesuskrastev.watodo.utilities.error_handling.InformationUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authService: AuthServiceImplementation,
    private val userRepository: UserRepository,
) : ViewModel() {
    private val _state = MutableStateFlow<LoginState>(LoginState())
    val state: StateFlow<LoginState> = _state

    fun onGoogleLoginSelected(googleLauncher: (GoogleSignInClient) -> Unit) {
        val gsc = authService.getGoogleClient()
        googleLauncher(gsc)
    }

    private fun loginWithGoogle(
        idToken: String,
        onRestartApp: () -> Unit,
    ) {
        _state.value = _state.value.copy(
            information = InformationUiState.Information(
                showProgress = true,
                message = "Iniciando sesión...",
            )
        )
        viewModelScope.launch {
            try {
                val result = authService.loginWithGoogle(idToken)
                withContext(Dispatchers.IO) {
                    val existUser = userRepository.getById(authService.getUser()?.uid!!) == null
                    if (existUser) userRepository.insert(User(id = authService.getUser()?.uid!!))
                }
                _state.value = _state.value.copy(
                    information = InformationUiState.Hidden()
                )
                if (result != null) onRestartApp()
            } catch (e: AuthServiceException) {
                _state.value = _state.value.copy(
                    information = InformationUiState.Error(
                        message = "Error al iniciar sesión",
                        onDismiss = {
                            _state.value = _state.value.copy(
                                information = InformationUiState.Hidden()
                            )
                        }
                    )
                )
            }
        }
    }

    private fun signIn(
        onRestartApp: () -> Unit,
    ) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                information = InformationUiState.Information(
                    showProgress = true,
                    message = "Iniciando sesión...",
                )
            )
            try {
                withContext(Dispatchers.IO) {
                    authService.signIn(_state.value.email, _state.value.password)
                }
                _state.value = _state.value.copy(
                    information = InformationUiState.Hidden()
                )
                onRestartApp()
            } catch (e: AuthServiceException) {
                _state.value = _state.value.copy(
                    information = InformationUiState.Error(
                        message = "Error al iniciar sesión",
                        onDismiss = {
                            _state.value = _state.value.copy(
                                information = InformationUiState.Hidden()
                            )
                        }
                    )
                )
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
                    onRestartApp = event.onRestartApp,
                )
            }

            is LoginEvent.OnLogin -> {
                signIn(
                    onRestartApp = event.onRestartApp,
                )
            }

            else -> {}
        }
    }
}