package com.example.draftt.auth.viewmodels

import androidx.lifecycle.ViewModel
import com.example.draftt.auth.repositories.FirebaseAuthRepository
import com.google.firebase.auth.FirebaseUser

class SignUpViewModel: ViewModel() {

    private val authRepository by lazy {
        FirebaseAuthRepository()
    }

    fun signup(email: String, password: String) = authRepository.signup(email, password)

    fun logout() = authRepository.logout()

}