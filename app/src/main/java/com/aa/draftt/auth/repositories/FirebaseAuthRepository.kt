package com.aa.draftt.auth.repositories

import com.aa.draftt.room.dao.UserDao
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirebaseAuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val userDao: UserDao
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
        return firebaseAuth.currentUser
    }

    // TODO: Add logging when implement this properly
    override suspend fun signout() = withContext(Dispatchers.IO) { firebaseAuth.signOut() }
}