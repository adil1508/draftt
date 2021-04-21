package com.example.draftt.auth.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.draftt.auth.repositories.AuthResult
import com.example.draftt.auth.repositories.FirebaseAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val authRepository: FirebaseAuthRepository) :
    ViewModel() {

    val authResult: LiveData<AuthResult> = authRepository.authResult

    fun signup(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.signup(email, password)
        }
    }

    fun logout() = authRepository.logout()

}