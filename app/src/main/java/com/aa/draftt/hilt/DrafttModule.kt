package com.aa.draftt.hilt

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DrafttModule {

    @Provides
    fun providesFirebaseAuthService(): FirebaseAuth{
        return Firebase.auth
    }

}