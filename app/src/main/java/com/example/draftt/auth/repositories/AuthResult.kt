package com.example.draftt.auth.repositories

import com.google.firebase.auth.FirebaseUser

data class AuthResult(val status: Boolean?, val error: String?, val user: FirebaseUser?)
