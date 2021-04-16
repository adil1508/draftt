package com.example.draftt.auth.repositories

import com.google.firebase.auth.FirebaseUser

// TODO: This interface seems really unnecessary
interface AuthRepository {
    fun login(email: String, password: String)
    fun signup(email: String, password: String)
    fun currentUser(): FirebaseUser?
    fun logout()
}