package com.aa.draftt.hilt

import com.aa.draftt.auth.repositories.AuthRepository
import com.aa.draftt.auth.repositories.FirebaseAuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DrafttProvidesModule {

    @Provides
    @Singleton
    fun providesFirebaseAuthService(): FirebaseAuth {
        return Firebase.auth
    }

}

@Module
@InstallIn(SingletonComponent::class)
abstract class DrafttBindingsModule {
    // for interfaces

    @Binds
    abstract fun bindAuthRepository(
        firebaseAuthRepository: FirebaseAuthRepository
    ): AuthRepository
}