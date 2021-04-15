package com.example.draftt.auth.repositories

interface AuthRepository {
    fun login(email: String, password: String)
    fun signup(email: String, password: String)
    fun currentUser()
    fun logout()
}