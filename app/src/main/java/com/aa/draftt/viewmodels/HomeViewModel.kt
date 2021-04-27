package com.aa.draftt.viewmodels

import androidx.lifecycle.ViewModel
import com.aa.draftt.auth.repositories.FirebaseAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val firebaseAuthRepository: FirebaseAuthRepository): ViewModel(){
}