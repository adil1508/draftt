package com.example.draftt.auth.viewmodels

import androidx.lifecycle.ViewModel
import com.example.draftt.auth.repositories.FirebaseAuthRepository

class SignUpViewModel : ViewModel() {

    private val authRepository by lazy {
        FirebaseAuthRepository()
    }

    fun signup(email: String, password: String) {
        authRepository.signup(email, password)
//        TODO: this is how we should be doing this
//        viewModelScope.launch {
//            authRepository.signup(email, password)
//        }
    }

    fun logout() = authRepository.logout()

}