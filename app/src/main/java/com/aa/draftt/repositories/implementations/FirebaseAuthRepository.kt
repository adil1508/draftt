package com.aa.draftt.repositories.implementations

import com.aa.draftt.repositories.AuthRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirebaseAuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override suspend fun login(
        email: String,
        password: String
    ): Task<AuthResult> {
        return withContext(Dispatchers.IO) {
            firebaseAuth.signInWithEmailAndPassword(email, password)
        }
    }

    override suspend fun signup(
        email: String,
        password: String
    ): Task<AuthResult> {
        return withContext(Dispatchers.IO) {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
        }
    }

    override suspend fun resetPassword(email: String): Task<Void> {
        return withContext(Dispatchers.IO){
            firebaseAuth.sendPasswordResetEmail(email)
        }
    }

    override fun getLoggedInUser(): FirebaseUser? = firebaseAuth.currentUser

    // TODO: Add logging when implement this properly
    override suspend fun signout() = withContext(Dispatchers.IO) {
        firebaseAuth.signOut()
    }
}