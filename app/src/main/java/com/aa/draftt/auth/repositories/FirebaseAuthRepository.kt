package com.aa.draftt.auth.repositories

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirebaseAuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) : AuthRepository {

    override suspend fun login(
        email: String,
        password: String
    ): Task<com.google.firebase.auth.AuthResult> {
        return withContext(Dispatchers.IO) {
            firebaseAuth.signInWithEmailAndPassword(email, password)
        }
    }

    override suspend fun signup(
        email: String,
        password: String
    ): Task<com.google.firebase.auth.AuthResult> {
        return withContext(Dispatchers.IO) {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
        }
    }

    override fun currentUser(): FirebaseUser? {
        // Need to figure out if we want to return user from firebase or local cache
        return firebaseAuth.currentUser
    }

    // TODO: Add logging when implement this properly
    override suspend fun signout() = withContext(Dispatchers.IO) {
        firebaseAuth.signOut()
    }
}