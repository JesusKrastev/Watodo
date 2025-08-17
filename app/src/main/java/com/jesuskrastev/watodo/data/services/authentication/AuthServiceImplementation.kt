package com.jesuskrastev.watodo.data.services.authentication

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.jesuskrastev.watodo.R
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthServiceImplementation @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    @ApplicationContext private val context: Context,
): AuthService {
    private val logTag: String = "AuthServiceImplementation"

    fun observeUser(): Flow<FirebaseUser?> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            trySend(firebaseAuth.currentUser) // emite null si no hay usuario
        }
        firebaseAuth.addAuthStateListener(listener)

        awaitClose { firebaseAuth.removeAuthStateListener(listener) }
    }

    fun observeUserLoggedIn(): Task<Boolean> {
        val taskCompletionSource = TaskCompletionSource<Boolean>()
        var authStateListener: FirebaseAuth.AuthStateListener? = null

        authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user: FirebaseUser? = firebaseAuth.currentUser
            if (user != null) taskCompletionSource.setResult(true)
        }

        firebaseAuth.addAuthStateListener(authStateListener)

        taskCompletionSource.task.addOnCompleteListener {
            firebaseAuth.removeAuthStateListener(authStateListener)
        }

        return taskCompletionSource.task
    }

    fun getUser(): FirebaseUser? = firebaseAuth.currentUser

    fun isAuthenticated(): Boolean = firebaseAuth.currentUser != null

    override fun resetEmail(email: String): Task<Void> =
        firebaseAuth.sendPasswordResetEmail(email)

    override fun signIn(email: String, password: String): Task<AuthResult> =
        firebaseAuth.signInWithEmailAndPassword(email, password)

    override fun signUp(email: String, password: String): Task<AuthResult> =
        firebaseAuth.createUserWithEmailAndPassword(email, password)

    override fun logOut() {
        // Email and password
        firebaseAuth.signOut()
        // Google
        getGoogleClient().signOut()
    }

    fun getGoogleClient(): GoogleSignInClient {
        val idToken = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        return GoogleSignIn.getClient(context, idToken)
    }

    override suspend fun loginWithGoogle(idToken: String): FirebaseUser? {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        val authResult = FirebaseAuth.getInstance().signInWithCredential(credential).await()

        return authResult.user
    }
}