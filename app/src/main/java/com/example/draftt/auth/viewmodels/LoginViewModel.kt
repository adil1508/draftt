package com.example.draftt.auth.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.draftt.auth.repositories.AuthResult
import com.example.draftt.auth.repositories.FirebaseAuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    val authResult: LiveData<AuthResult>

    // TODO: This should be injected
    private val authRepository by lazy {
        FirebaseAuthRepository()
    }

    init {
        authResult = authRepository.authResult
    }

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.login(email, password)
        }
    }

}