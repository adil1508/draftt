package com.example.draftt.auth.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.draftt.auth.repositories.FirebaseAuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {

    private val authRepository by lazy {
        FirebaseAuthRepository()
    }

    fun signup(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.signup(email, password)
        }
    }

    fun logout() = authRepository.logout()

}