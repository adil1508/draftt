package com.example.draftt.auth.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import timber.log.Timber

// TODO: This class will be injected into FirebaseAuthRepository
class FirebaseAuthenticationService {

    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }

    fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Timber.d("Successfully logged in user with email: $email")
            } else {
                Timber.d("Failed to log in user with email: $email")
            }
        }
    }

    fun signup(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Timber.d("Successfully created user with email: $email")
            } else {
                Timber.d("FAILED to create user with email: $email")
            }
        }

    }

    fun currentUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun logout() = auth.signOut()

}