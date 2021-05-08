package com.aa.draftt.hilt

import android.content.Context
import androidx.room.Room
import com.aa.draftt.repositories.AuthRepository
import com.aa.draftt.repositories.UserRepository
import com.aa.draftt.repositories.implementations.FirebaseAuthRepository
import com.aa.draftt.repositories.implementations.FirestoreUserRepository
import com.aa.draftt.room.DatabaseMigrations
import com.aa.draftt.room.DrafttDatabase
import com.aa.draftt.room.dao.UserDao
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
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
    @Singleton
    fun providesFirebaseFirestore(): FirebaseFirestore {
        return Firebase.firestore
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
        ).addMigrations(
            DatabaseMigrations.MIGRATION_1_2
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

    @Binds
    abstract fun bindUserRepository(
        firestoreUserRepository: FirestoreUserRepository
    ): UserRepository

}