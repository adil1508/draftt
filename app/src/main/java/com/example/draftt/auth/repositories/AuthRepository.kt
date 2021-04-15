package com.example.draftt.auth.repositories

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    fun login(email: String, password: String)
    fun signup(email: String, password: String)
    fun currentUser(): FirebaseUser?
    fun logout()
}