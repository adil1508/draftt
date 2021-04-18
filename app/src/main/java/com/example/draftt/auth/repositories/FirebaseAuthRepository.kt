package com.example.draftt.auth.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import timber.log.Timber

class FirebaseAuthRepository {

    private val _authResult = MutableLiveData(
        AuthResult(
            status = null,
            error = null
        )
    )
    val authResult: LiveData<AuthResult>
        get() {
            return _authResult
        }

    // Firebase Auth Service
    private val firebaseAuth: FirebaseAuth by lazy {
        Firebase.auth
    }

    fun login(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Timber.d("Successfully logged in user with email: $email")
                _authResult.postValue(AuthResult(status = true, error = null))
            } else {
                Timber.d("Failed to log in user with email: $email")
                _authResult.postValue(AuthResult(status = false, error = task.exception.toString()))
            }
        }
    }

    fun signup(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Timber.d("Successfully created user with email: $email")
                _authResult.postValue(AuthResult(status = true, error = null))
            } else {
                Timber.d("FAILED to create user with email: $email")
                _authResult.postValue(AuthResult(status = false, error = task.exception.toString()))
            }
        }

    }

    fun currentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    fun logout() = firebaseAuth.signOut()
}