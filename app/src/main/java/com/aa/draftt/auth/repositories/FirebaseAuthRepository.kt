package com.aa.draftt.auth.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class FirebaseAuthRepository @Inject constructor(private val firebaseAuth: FirebaseAuth) {

    suspend fun login(email: String, password: String): Task<com.google.firebase.auth.AuthResult> {
        return withContext(Dispatchers.IO) {
            firebaseAuth.signInWithEmailAndPassword(email, password)
        }
    }

    suspend fun signup(email: String, password: String): Task<com.google.firebase.auth.AuthResult> {
        return withContext(Dispatchers.IO) {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
        }
    }

    fun currentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    // TODO: Add logging when implement this properly
    suspend fun signout() = withContext(Dispatchers.IO) { firebaseAuth.signOut() }
}