package com.aa.draftt.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aa.draftt.auth.repositories.FirebaseAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val firebaseAuthRepository: FirebaseAuthRepository): ViewModel(){
    fun signout(){
        viewModelScope.launch(Dispatchers.IO) {
            firebaseAuthRepository.signout()
        }
    }
}