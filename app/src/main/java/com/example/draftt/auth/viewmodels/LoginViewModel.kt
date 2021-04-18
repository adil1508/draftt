package com.example.draftt.auth.viewmodels

import androidx.lifecycle.ViewModel
import com.example.draftt.auth.repositories.FirebaseAuthRepository

class LoginViewModel : ViewModel() {

    // TODO: This should be injected
    private val authRepository by lazy {
        FirebaseAuthRepository()
    }

    fun login(email: String, password: String) {
        authRepository.login(email, password)
    }

}