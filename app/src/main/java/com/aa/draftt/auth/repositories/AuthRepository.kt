package com.aa.draftt.auth.repositories

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference

interface AuthRepository {

    suspend fun login(email: String, password: String): Task<AuthResult>

    suspend fun signup(email: String, password: String): Task<AuthResult>

    // This will go into a FireStoreRepo
    suspend fun writeToFirestore(name: String, email: String): Task<DocumentReference>

    suspend fun currentUser(): FirebaseUser?

    // TODO: Add logging when implement this properly
    suspend fun signout()
}