package com.jesuskrastev.watodo.data.services.authentication

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

interface AuthService {
    fun resetEmail(email: String): Task<Void>

    fun signIn(email: String, password: String): Task<AuthResult>

    fun signUp(email: String, password: String): Task<AuthResult>

    fun logOut()

    suspend fun loginWithGoogle(idToken: String): FirebaseUser?
}