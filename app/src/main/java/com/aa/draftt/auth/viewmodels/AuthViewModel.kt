package com.aa.draftt.auth.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aa.draftt.auth.repositories.AuthResult
import com.aa.draftt.auth.repositories.FirebaseAuthRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: FirebaseAuthRepository): ViewModel() {

    val authResult: LiveData<AuthResult> = authRepository.authResult
    val user: LiveData<FirebaseUser> = authRepository.firebaseUser

    init {
        runBlocking{
            authRepository.currentUser()
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.login(email, password)
        }
    }

    fun signup(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.signup(email, password)
        }
    }

}