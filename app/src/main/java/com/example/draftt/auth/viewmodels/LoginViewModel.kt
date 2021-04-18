package com.example.draftt.auth.viewmodels

import androidx.lifecycle.ViewModel
import com.example.draftt.auth.repositories.FirebaseAuthRepository

class LoginViewModel: ViewModel() {

    private val authRepository by lazy {
        FirebaseAuthRepository()
    }

    fun login(email: String, password: String){
        authRepository.login(email, password)
    }

}