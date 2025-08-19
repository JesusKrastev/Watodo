package com.jesuskrastev.watodo.ui.features.login

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.jesuskrastev.watodo.R
import com.jesuskrastev.watodo.ui.composables.CoroutineManagementSnackBar
import com.jesuskrastev.watodo.ui.composables.SnackbarCommon
import com.jesuskrastev.watodo.ui.navigation.ActivitiesRoute
import com.jesuskrastev.watodo.ui.navigation.Destination
import com.jesuskrastev.watodo.utilities.device.registerGoogleLauncher

@Composable
fun Fields(
    modifier: Modifier = Modifier,
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = onEmailChange,
            placeholder = {
                Text(text = "Correo electrónico")
            },
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = onPasswordChange,
            placeholder = {
                Text(text = "Contraseña")
            },
        )
    }
}

@Composable
fun ForgotPassword(
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier.clickable(onClick = { /*TODO*/ }),
        text = "¿Olvidaste tu contraseña?",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.primary,
    )
}

@Composable
fun LoginButton(
    modifier: Modifier = Modifier,
    onLogin: () -> Unit,
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        onClick = onLogin,
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = "Iniciar sesión",
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
fun LoginDivider(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
        )
        Text(
            text = "O continuar con",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
        )
    }
}

@Composable
fun GoogleLoginButton(
    modifier: Modifier = Modifier,
    onLoginWithGoogle: (String) -> Unit,
    onGoogleLoginSelected: (googleLauncher: (GoogleSignInClient) -> Unit) -> Unit,
) {
    val googleLauncher = registerGoogleLauncher(
        loginWithGoogle = onLoginWithGoogle,
    )

    OutlinedButton(
        modifier = modifier.fillMaxWidth(),
        onClick = {
            onGoogleLoginSelected {
                googleLauncher.launch(it.signInIntent)
            }
        },
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                modifier = Modifier.size(18.dp),
                painter = painterResource(R.drawable.google_logo),
                contentDescription = "Google",
            )
            Text(
                text = "Iniciar sesión con Google",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

@Composable
fun Register(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
    ) {
        Text(
            text = "¿No tienes una cuenta? ",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
            text = "Regístrate",
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@Composable
fun WelcomeTitle(
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier,
        text = "Inicia sesión",
        fontWeight = FontWeight.SemiBold,
        fontSize = 34.sp,
    )
}

@Composable
fun WelcomeSubtitle(
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier,
        text = "Introduce tu email y contraseña para continuar.",
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}

@Composable
fun LoginContent(
    modifier: Modifier = Modifier,
    state: LoginState,
    onEvent: (LoginEvent) -> Unit,
) {
    val context = LocalContext.current
    val activity = context as? android.app.Activity
    val onRestartApp = {
        activity?.let {
            val intent = it.intent
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            it.finish()
            it.startActivity(intent)
        }
    }

    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        WelcomeTitle()
        WelcomeSubtitle()
        Fields(
            email = state.email,
            password = state.password,
            onEmailChange = { onEvent(LoginEvent.OnEmailChanged(it)) },
            onPasswordChange = { onEvent(LoginEvent.OnPasswordChanged(it)) },
        )
        ForgotPassword(
            modifier = Modifier.align(Alignment.End),
        )
        LoginButton(
            onLogin = {
                onEvent(
                    LoginEvent.OnLogin(
                        onRestartApp = { onRestartApp() },
                    )
                )
            }
        )
        LoginDivider()
        GoogleLoginButton(
            onGoogleLoginSelected = { googleLauncher ->
                onEvent(LoginEvent.OnGoogleLoginSelected(googleLauncher))
            },
            onLoginWithGoogle = { idToken ->
                onEvent(
                    LoginEvent.OnLoginWithGoogle(
                        idToken = idToken,
                        onRestartApp = { onRestartApp() },
                    )
                )
            }
        )
        Register()
    }
}

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    state: LoginState,
    onEvent: (LoginEvent) -> Unit,
) {
    val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }

    CoroutineManagementSnackBar(
        snackbarHostState = snackbarHostState,
        informationState = state.information,
    )

    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets.statusBars,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) {
                SnackbarCommon(
                    informationState = state.information
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            LoginContent(
                state = state,
                onEvent = onEvent,
            )
        }
    }

}