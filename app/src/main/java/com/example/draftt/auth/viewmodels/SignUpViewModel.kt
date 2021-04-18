package com.example.draftt.auth.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.draftt.auth.repositories.AuthResult
import com.example.draftt.auth.repositories.FirebaseAuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {

    val authResult: LiveData<AuthResult>

    private val authRepository by lazy {
        FirebaseAuthRepository()
    }

    init {
        authResult = authRepository.authResult
    }

    fun signup(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.signup(email, password)
        }
    }

    fun logout() = authRepository.logout()

}