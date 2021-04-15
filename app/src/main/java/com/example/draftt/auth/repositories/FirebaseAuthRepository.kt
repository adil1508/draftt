package com.example.draftt.auth.repositories

import com.google.firebase.auth.FirebaseUser

class FirebaseAuthRepository: AuthRepository {

    // TODO: Needs an instance of Firebase Auth

    override fun login(email: String, password: String) {
        TODO("Not yet implemented")
    }

    override fun signup(email: String, password: String) {
        TODO("Not yet implemented")
    }

    override fun currentUser(): FirebaseUser? {
        TODO("Not yet implemented")
    }

    override fun logout() {
        TODO("Not yet implemented")
    }
}