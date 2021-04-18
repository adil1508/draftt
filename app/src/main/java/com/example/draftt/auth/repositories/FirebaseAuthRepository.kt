package com.example.draftt.auth.repositories

import com.google.firebase.auth.FirebaseUser

class FirebaseAuthRepository : AuthRepository {

    // TODO: Needs an instance of Firebase Auth, which should be injected
    private val firebase by lazy {
        FirebaseAuthenticationService()
    }

    override fun login(email: String, password: String) {
        firebase.login(email, password)
    }

    override fun signup(email: String, password: String) {
        firebase.signup(email, password)
    }

    override fun currentUser(): FirebaseUser? {
        return firebase.currentUser()
    }

    override fun logout() {
        firebase.logout()
    }
}