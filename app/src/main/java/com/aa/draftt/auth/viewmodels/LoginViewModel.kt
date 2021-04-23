package com.aa.draftt.auth.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aa.draftt.auth.repositories.AuthResult
import com.aa.draftt.auth.repositories.FirebaseAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authRepository: FirebaseAuthRepository) :
    ViewModel() {

    val authResult: LiveData<AuthResult> = authRepository.authResult

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.login(email, password)
        }
    }

}