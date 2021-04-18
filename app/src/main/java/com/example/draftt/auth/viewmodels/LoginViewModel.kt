package com.example.draftt.auth.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.draftt.auth.repositories.AuthResult
import com.example.draftt.auth.repositories.FirebaseAuthRepository

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
        authRepository.login(email, password)
    }

}