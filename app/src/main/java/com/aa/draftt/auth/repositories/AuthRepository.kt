package com.aa.draftt.auth.repositories

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {

    suspend fun login(email: String, password: String): Task<AuthResult>

    suspend fun signup(email: String, password: String): Task<AuthResult>

    fun currentUser(): FirebaseUser?

    // TODO: Add logging when implement this properly
    suspend fun signout()
}