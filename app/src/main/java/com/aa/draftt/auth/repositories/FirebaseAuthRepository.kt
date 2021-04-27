package com.aa.draftt.auth.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class FirebaseAuthRepository @Inject constructor() {

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    private val _authResult = MutableLiveData(
        AuthResult(status = null, error = null)
    )
    val authResult: LiveData<AuthResult>
        get() = _authResult

    private val _firebaseUser = MutableLiveData<FirebaseUser>()
    val firebaseUser: LiveData<FirebaseUser>
        get() = _firebaseUser

    // Firebase Auth Service
    private val firebaseAuth: FirebaseAuth by lazy {
        Firebase.auth
    }

    suspend fun login(email: String, password: String) {
        withContext(ioDispatcher) {
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Timber.d("Successfully logged in user with email: $email")
                    _authResult.postValue(AuthResult(status = true, error = null))
                    _firebaseUser.postValue(firebaseAuth.currentUser)
                } else {
                    Timber.d("Failed to log in user with email: $email")
                    _authResult.postValue(
                        AuthResult(
                            status = false,
                            error = task.exception.toString()
                        )
                    )
                }
            }
        }
    }

    suspend fun signup(email: String, password: String) {
        withContext(ioDispatcher) {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Timber.d("Successfully created user with email: $email")
                        _authResult.postValue(AuthResult(status = true, error = null))
                        _firebaseUser.postValue(firebaseAuth.currentUser)
                    } else {
                        Timber.d("FAILED to create user with email: $email")
                        _authResult.postValue(
                            AuthResult(
                                status = false,
                                error = task.exception.toString()
                            )
                        )
                    }
                }
        }

    }

    fun currentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    // TODO: Add logging when implement this properly
    fun logout() = firebaseAuth.signOut()
}