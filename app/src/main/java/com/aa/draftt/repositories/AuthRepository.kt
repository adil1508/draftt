package com.aa.draftt.repositories

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QuerySnapshot

interface AuthRepository {

    suspend fun login(email: String, password: String): Task<AuthResult>

    suspend fun signup(email: String, password: String): Task<AuthResult>

    fun getLoggedInUser(): FirebaseUser?

    suspend fun resetPassword(email: String): Task<Void>

    // TODO: Add logging when implement this properly
    suspend fun signout()
}