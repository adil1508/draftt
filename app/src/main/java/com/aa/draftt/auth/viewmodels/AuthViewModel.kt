package com.aa.draftt.auth.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aa.draftt.auth.repositories.AuthRepository
import com.aa.draftt.auth.repositories.AuthResult
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: AuthRepository) :
    ViewModel() {

    // Stores results of making login/signup calls
    private val _authResult = MutableLiveData(
        AuthResult(status = null, error = null)
    )
    val authResult: LiveData<AuthResult>
        get() = _authResult

    // Stores current logged in user
    private val _firebaseUser = MutableLiveData<FirebaseUser>(currentUser())
    val firebaseUser: LiveData<FirebaseUser?>
        get() = _firebaseUser


    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val task = authRepository.login(email, password)
            task.addOnCompleteListener {
                if (task.isSuccessful) {
                    Timber.d("Successfully logged in user with email: $email")
                    // Instead of posting the value of it.result!!.user, we need to get it from db
                    _firebaseUser.postValue(it.result!!.user)
                    _authResult.postValue(AuthResult(status = true, error = null))

                } else {
                    Timber.d("Failed to log in user with email: $email")
                    _firebaseUser.postValue(null)
                    _authResult.postValue(
                        AuthResult(
                            status = false,
                            error = task.exception?.localizedMessage.toString()
                        )
                    )
                }
            }
        }
    }

    fun signup(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val task = authRepository.signup(email, password)
            task.addOnCompleteListener {
                if (task.isSuccessful) {
                    Timber.d("Successfully signed up user with email: $email")
                    // Need to write user info in the db
                    writeUserInfoToDB()
                    // Instead of posting the value of it.result!!.user, we need to get it from db
                    _firebaseUser.postValue(it.result!!.user)
                    _authResult.postValue(AuthResult(status = true, error = null))
                } else {
                    Timber.d("Failed to sign up user with email: $email")
                    _firebaseUser.postValue(null)
                    _authResult.postValue(
                        AuthResult(
                            status = false,
                            error = task.exception?.localizedMessage.toString()
                        )
                    )
                }
            }
        }
    }

    private fun writeUserInfoToDB(){
        viewModelScope.launch {
            authRepository.writeUserToDatabase("Test", "Test@test.com")
        }
    }

    // updates the current user live data
    private fun currentUser(): FirebaseUser? {
        return authRepository.currentUser()
    }

}