package com.aa.draftt.auth.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class FirebaseAuthRepository @Inject constructor(private val firebaseAuth: FirebaseAuth) {

    // Stores results of making login/signup calls
    private val _authResult = MutableLiveData(
        AuthResult(status = null, error = null)
    )
    val authResult: LiveData<AuthResult>
        get() = _authResult

    // Stores current logged in user
    private val _firebaseUser = MutableLiveData(firebaseAuth.currentUser)
    val firebaseUser: LiveData<FirebaseUser?>
        get() = _firebaseUser

    suspend fun login(email: String, password: String) {
        withContext(Dispatchers.IO) {
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Timber.d("Successfully logged in user with email: $email")
                    _firebaseUser.postValue(firebaseAuth.currentUser)
                    _authResult.postValue(AuthResult(status = true, error = null))
                } else {
                    Timber.d("Failed to log in user with email: $email")
                    _firebaseUser.postValue(null)
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
        withContext(Dispatchers.IO) {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Timber.d("Successfully created user with email: $email")
                        _firebaseUser.postValue(firebaseAuth.currentUser)
                        _authResult.postValue(AuthResult(status = true, error = null))
                    } else {
                        Timber.d("FAILED to create user with email: $email")
                        _firebaseUser.postValue(null)
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

    suspend fun currentUser() {
        withContext(Dispatchers.IO) {
            _firebaseUser.postValue(firebaseAuth.currentUser)
        }
    }

    // TODO: Add logging when implement this properly
    suspend fun signout() = withContext(Dispatchers.IO) { firebaseAuth.signOut() }
}