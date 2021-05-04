package com.aa.draftt.hilt

import android.content.Context
import androidx.room.Room
import com.aa.draftt.auth.repositories.AuthRepository
import com.aa.draftt.auth.repositories.FirebaseAuthRepository
import com.aa.draftt.room.DrafttDatabase
import com.aa.draftt.room.dao.UserDao
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    fun provideUserDao(drafttDatabase: DrafttDatabase): UserDao {
        return drafttDatabase.userDao()
    }

    // provides the dependency needed by Dao providers
    @Provides
    @Singleton
    fun providesDrafttDatabase(@ApplicationContext context: Context): DrafttDatabase {
        return Room.databaseBuilder(
            context,
            DrafttDatabase::class.java,
            "DrafttDatabase"
        ).build()
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