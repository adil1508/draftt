package com.aa.draftt.hilt

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
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
}